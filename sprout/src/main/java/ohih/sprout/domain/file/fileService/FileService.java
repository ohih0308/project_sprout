package ohih.sprout.domain.file.fileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Utilities;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.dto.EventFileDto;
import ohih.sprout.domain.file.dto.FileDto;
import ohih.sprout.domain.file.fileMapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    @Value("#{fileUploadDirConfig[profileImageDir]}")
    private String profileImageDir;
    @Value("#{fileUploadDirConfig[attachedFileDir]}")
    private String attachedFileDir;
    @Value("#{fileUploadDirConfig[eventFileDir]}")
    private String eventFileDir;
    @Value("#{fileUploadDirConfig[adFileDir]}")
    private String adFileDir;


    private final FileMapper fileMapper;


    private FileDto extractFileDtoFromMultipartFile(MultipartFile multipartFile, Long id) {
        String oriFileName = multipartFile.getOriginalFilename();
        String savedFileName = Utilities.createUUID();
        String ext = Utilities.getExt(oriFileName);
        Long size = multipartFile.getSize();

        return new FileDto(id, oriFileName, savedFileName, ext, size);
    }


    // profile images
    private int saveProfileImage_db(FileDto fileDto) {
        return fileMapper.saveProfileImage_db(fileDto);
    }

    private void saveProfileImage_prj(String savedFileName, String ext,
                                      MultipartFile multipartFile) throws IOException {
        String fullPath = profileImageDir + savedFileName + "." + ext;
        File file = new File(fullPath);

        multipartFile.transferTo(file);
    }

    public void saveProfileImage(Long userId, MultipartFile multipartFile) throws IOException, SQLException {
        if (multipartFile.isEmpty()) {
            return;
        }

        FileDto fileDto = extractFileDtoFromMultipartFile(multipartFile, userId);

        if (saveProfileImage_db(fileDto) != 1) {
            throw new SQLException();
        }

        saveProfileImage_prj(fileDto.getSavedFileName(), fileDto.getExt(), multipartFile);

    }

    public void updateProfileImage(Long userId, MultipartFile multipartFile) throws IOException, SQLException {
        fileMapper.deleteProfileImageByUserId(userId);
        saveProfileImage(userId, multipartFile);
    }

    public FileDto getProfileImageByUserId(Long userId) {
        return fileMapper.getProfileImageByUserId(userId);
    }


    // post attached files
    private int saveAttachedFile_db(FileDto fileDto) {
        return fileMapper.saveAttachedFile_db(fileDto);
    }

    private void saveAttachedFile_prj(String dir, MultipartFile multipartFile) throws IOException {
        File file = new File(dir);

        multipartFile.transferTo(file);
    }

    public void saveAttachedFile(Long postId, String boardName, MultipartFile multipartFile) throws IOException, SQLException {
        if (multipartFile.isEmpty()) {
            return;
        }

        FileDto fileDto = extractFileDtoFromMultipartFile(multipartFile, postId);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StringConst.ATTACHED_FILES_FOLDER_NAME_FORMAT);

        String path = attachedFileDir + boardName + "/" + now.format(formatter);

        File file = new File(path);

        file.mkdirs();

        String fullPath = path + "/" + fileDto.getSavedFileName() + "." + fileDto.getExt();

        fileDto.setDir(fullPath);

        int result = saveAttachedFile_db(fileDto);

        if (result != 1) {
            throw new SQLException();
        }

        saveAttachedFile_prj(fullPath, multipartFile);
    }


    public List<FileDto> getAttachedFileListByPostId(Long postId) {
        return fileMapper.getAttachedFileListByPostId(postId);
    }

    public String getDirBySavedFileName(String savedFileName) {
        return fileMapper.getDirBySavedFileName(savedFileName);
    }


    private int deleteAttachedFile_db(String savedFileName) {
        return fileMapper.deleteAttachedFile_dbBySavedFileName(savedFileName);
    }

    private Boolean deleteAttachedFile_prj(String dir) {
        File file = new File(dir);

        return file.delete();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAttachedFile(String savedFileName) throws IOException, SQLException {
        String dir = getDirBySavedFileName(savedFileName);

        if (!deleteAttachedFile_prj(dir)) {
            throw new IOException();
        }

        if (deleteAttachedFile_db(savedFileName) != 1) {
            throw new SQLException();
        }
    }

    public int deleteAttachedFilesByPostId(Long postId) {
        return fileMapper.deleteAttachedFilesByPostId(postId);
    }

    public int getAttachedFileCountByPostId(Long postId) {
        return fileMapper.getAttachedFileCountByPostId(postId);
    }

    public String getOriFileNameBySavedFileName(String savedFileName) {
        return fileMapper.getOriFileNameBySavedFileName(savedFileName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProfileImage(Long userId, String savedFileName, String ext) {
        deleteProfileImage_db(userId);
        deleteProfileImage_prj(savedFileName, ext);
    }

    public void deleteProfileImage_prj(String savedFileName, String ext) {
        File file = new File(profileImageDir + savedFileName + "." + ext);

        file.delete();
    }

    public void deleteProfileImage_db(Long userId) {
        fileMapper.deleteProfileImage_prj(userId);
    }

    public void saveEventFile(MultipartFile multipartFile, String eventName, String link) throws IOException, SQLException {
        if (multipartFile.isEmpty()) {
            return;
        }

        EventFileDto eventFileDto = new EventFileDto(eventName,
                multipartFile.getOriginalFilename(),
                Utilities.createUUID(),
                Utilities.getExt(multipartFile.getOriginalFilename()),
                multipartFile.getSize(),
                link);

        saveEventFile_prj(eventFileDto.getSavedFileName(), eventFileDto.getExt(), multipartFile);

        if (saveEvent_db(eventFileDto) != 1) {
            throw new SQLException();
        }
    }

    public void saveEventFile_prj(String savedFileName, String ext,
                                  MultipartFile multipartFile) throws IOException {
        String fullPath = eventFileDir + savedFileName + "." + ext;
        File file = new File(fullPath);

        multipartFile.transferTo(file);
    }

    public int saveEvent_db(EventFileDto eventFileDto) {
        return fileMapper.saveEventFile_db(eventFileDto);
    }

    public void saveAdFile(MultipartFile multipartFile, String provider, String link) throws IOException, SQLException {
        if (multipartFile.isEmpty()) {
            return;
        }

        AdFileDto adFileDto = new AdFileDto(
                provider,
                multipartFile.getOriginalFilename(),
                Utilities.createUUID(),
                Utilities.getExt(multipartFile.getOriginalFilename()),
                multipartFile.getSize(),
                link
        );

        saveAdFile_prj(adFileDto.getSavedFileName(), adFileDto.getExt(), multipartFile);

        if (saveAdFile_db(adFileDto) != 1) {
            throw new SQLException();
        }
    }

    public void saveAdFile_prj(String savedFileName, String ext, MultipartFile multipartFile) throws IOException {
        String fullPath = adFileDir + savedFileName + "." + ext;
        File file = new File(fullPath);

        multipartFile.transferTo(file);
    }

    public int saveAdFile_db(AdFileDto adFileDto) {
        return fileMapper.saveAdFile_db(adFileDto);
    }

    public List<EventFileDto> getEventFileList() {
        return fileMapper.getEventFileList();
    }

    public List<AdFileDto> getAdFileList() {
        return fileMapper.getAdFileList();
    }

    public void deleteEventFile(String savedFileName, String ext) throws SQLException {
        deleteEventFile_prj(savedFileName, ext);

        deleteEventFile_db(savedFileName);
    }

    public void deleteEventFile_prj(String savedFileName, String ext) {
        File file = new File(eventFileDir + savedFileName + "." + ext);

        file.delete();
    }

    public void deleteEventFile_db(String savedFileName) throws SQLException {
        if (fileMapper.deleteEventFile(savedFileName) != 1) {
            throw new SQLException();
        }
    }
}

package ohih.sprout.domain.file.fileMapper;

import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.dto.EventFileDto;
import ohih.sprout.domain.file.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    int saveProfileImage_db(FileDto fileDto);

    FileDto getProfileImageByUserId(Long userId);

    int deleteProfileImageByUserId(Long userId);

    int saveAttachedFile_db(FileDto fileDto);

    List<FileDto> getAttachedFileListByPostId(Long postId);

    int deleteAttachedFile_dbBySavedFileName(String savedFileName);

    String getDirBySavedFileName(String savedFileName);

    int getAttachedFileCountByPostId(Long postId);

    int deleteAttachedFilesByPostId(Long postId);

    String getOriFileNameBySavedFileName(String savedFileName);

    void deleteProfileImage_prj(Long userId);

    int saveEventFile_db(EventFileDto eventFileDto);

    int saveAdFile_db(AdFileDto adFileDto);

    List<EventFileDto> getEventFileList();

    List<AdFileDto> getAdFileList();

    int deleteEventFile(String savedFileName);
}

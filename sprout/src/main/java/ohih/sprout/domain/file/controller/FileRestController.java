package ohih.sprout.domain.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.file.dto.EventFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileRestController {


    private final MessageSource messageSource;
    private final FileService fileService;


    @PostMapping("/download")
    public void downloadAttachedFile(HttpServletResponse response, String savedFileName) {
        log.info("savedFileName = {}", savedFileName);

        String dir = fileService.getDirBySavedFileName(savedFileName);

        log.info("dir = {}", dir);

        String oriFileName = fileService.getOriFileNameBySavedFileName(savedFileName);

        response.setHeader("Content-Disposition", "attachment; filename=" + oriFileName);

        File file = new File(dir);

        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream fos = response.getOutputStream();
            int read = 0;
            byte[] buffer = new byte[1024];
            while ((read = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/save/event-file")
    public SimpleResponse saveEventFile(HttpServletRequest request,
                                        MultipartFile multipartFile,
                                        String eventName, String link) throws SQLException, IOException {
        fileService.saveEventFile(multipartFile, eventName, link);

        SimpleResponse simpleResponse = new SimpleResponse();

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("fileSaved",
                null,
                request.getLocale()));


        return simpleResponse;
    }


    @PostMapping("/save/ad-file")
    public SimpleResponse saveAdFile(HttpServletRequest request,
                                     MultipartFile multipartFile,
                                     String provider, String link) throws SQLException, IOException {
        fileService.saveAdFile(multipartFile, provider, link);

        SimpleResponse simpleResponse = new SimpleResponse();

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("fileSaved",
                null,
                request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/delete/event-file")
    public SimpleResponse deleteEventFile(HttpServletRequest request,
                                          String savedFileName, String ext) throws SQLException {

        fileService.deleteEventFile(savedFileName, ext);

        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("fileDeleted", null, request.getLocale()));

        return simpleResponse;
    }
}

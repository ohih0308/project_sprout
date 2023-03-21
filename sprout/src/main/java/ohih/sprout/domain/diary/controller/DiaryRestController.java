package ohih.sprout.domain.diary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Utilities;
import ohih.sprout.domain.diary.dto.DiaryConfigUpdateDto;
import ohih.sprout.domain.diary.dto.DiaryEditDto;
import ohih.sprout.domain.diary.service.DiaryService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Slf4j
public class DiaryRestController {

    private final MessageSource messageSource;

    private final DiaryService diaryService;


    @PostMapping("/activate")
    public SimpleResponse activateDiary(HttpServletRequest request,
                                        @SessionAttribute LoginMemberDto loginMember) throws SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        diaryService.activateDiary(loginMember.getUserId());

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("activateDiary", null, request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/deactivate")
    public SimpleResponse deactivateDiary(HttpServletRequest request,
                                          @SessionAttribute LoginMemberDto loginMember) throws SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        diaryService.deactivateDiary(loginMember.getUserId());

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("deactivateDiary", null, request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/update/config")
    public SimpleResponse updateDiaryConfig(HttpServletRequest request,
                                            @SessionAttribute LoginMemberDto loginMemberDto,
                                            DiaryConfigUpdateDto diaryConfigUpdateDto) throws SQLException {

        SimpleResponse simpleResponse = new SimpleResponse();

        diaryService.updateDiaryConfig(loginMemberDto.getUserId(), diaryConfigUpdateDto);

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("updateDiaryConfig", null, request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/write")
    public SimpleResponse writeDiary(HttpServletRequest request,
                                     @Nullable @SessionAttribute LoginMemberDto loginMember,
                                     @Validated DiaryEditDto diaryWriteDto, BindingResult bindingResult)
            throws BindException, SQLException {

        log.info("diaryWriteDto.toString() = {}", diaryWriteDto.toString());

        if (bindingResult.hasErrors()) {

            throw new BindException(bindingResult);
        }

        if (!diaryService.getDiaryActivationStatusByUserId(diaryWriteDto.getOwner())) {
            throw new InvalidPropertyException(String.class, StringConst.OWNER, null);
        }

        String userIp = Utilities.getIp(request);

        if (loginMember == null) {
            diaryService.writeDiary(userIp, diaryWriteDto);
        } else {
            diaryService.writeDiary(userIp, loginMember.getUserId(), loginMember.getName(), diaryWriteDto);
        }

        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("writeDiary", null, request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/{diaryId}/delete")
    public SimpleResponse deleteDiary(HttpServletRequest request,
                                      @PathVariable Long diaryId,
                                      @Nullable @SessionAttribute LoginMemberDto loginMemberDto,
                                      String pw) throws SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        if (loginMemberDto == null && diaryService.checkPostAccessAuthority(diaryId, pw)
                || loginMemberDto != null && diaryService.checkPostAccessAuthority(diaryId, loginMemberDto.getUserId())) {
            diaryService.deleteDiaryByDiaryId(diaryId);

            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteDiary",
                    null,
                    request.getLocale()
            ));
        }

        return simpleResponse;
    }
}

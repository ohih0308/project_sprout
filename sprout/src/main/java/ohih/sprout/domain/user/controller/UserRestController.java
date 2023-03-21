package ohih.sprout.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.domain.user.dto.Name;
import ohih.sprout.domain.user.dto.Pw;
import ohih.sprout.domain.user.service.UserService;
import ohih.sprout.exception.customException.ValueDuplicatedException;
import ohih.sprout.session.SessionMethods;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {

    private final SessionMethods sessionMethods;
    private final MessageSource messageSource;

    private final UserService userService;
    private final FileService fileService;


    @PostMapping("/getLoginMember")
    public LoginMemberDto getLoginMember(@Nullable @SessionAttribute LoginMemberDto loginMember) {
        return loginMember;
    }

    @PostMapping("/verify-name")
    public SimpleResponse verifyName(HttpServletRequest request,
                                     @Validated Name name, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        if (userService.checkNameDuplicated(name.getName())) {
            throw new ValueDuplicatedException(StringConst.NAME, StringConst.NAME, name.getName());
        }

        sessionMethods.setSessionAttribute(request, StringConst.VERIFIED_NAME, name.getName());

        SimpleResponse simpleResponse = new SimpleResponse();

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage(
                "name.name.verified",
                new String[]{name.getName()},
                request.getLocale()
        ));

        return simpleResponse;
    }

    @PostMapping("/verify-pw")
    public SimpleResponse verifyPw(HttpServletRequest request,
                                   @Validated Pw pw, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        SimpleResponse simpleResponse = new SimpleResponse();

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage(
                "pw.pw.verified",
                null,
                request.getLocale()
        ));

        return simpleResponse;
    }

    @PostMapping("/verify-pw-confirmation")
    public SimpleResponse verifyPwConfirmation(HttpServletRequest request,
                                               Pw pw) {

        SimpleResponse simpleResponse = new SimpleResponse();

        if (pw.getPw().equals(pw.getConfirmation())) {
            sessionMethods.setSessionAttribute(request, StringConst.VERIFIED_PW, pw.getPw());

            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage(
                    "pw.pwConfirmation.verified",
                    null,
                    request.getLocale()
            ));
        } else {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage(
                    "pw.pwConfirmation.notVerified",
                    null,
                    request.getLocale()
            ));
        }

        return simpleResponse;
    }

    @PostMapping("/update-name")
    public SimpleResponse updateName(HttpServletRequest request,
                                     @SessionAttribute LoginMemberDto loginMember, String name) throws SQLException {

        int executionResult = 0;
        String verifiedName = (String) sessionMethods.getSessionAttribute(request, StringConst.VERIFIED_NAME);

        SimpleResponse simpleResponse = new SimpleResponse();

        if (verifiedName.equals(name)) {
            executionResult = userService.updateName(loginMember.getUserId(), name);
        } else {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage(
                    "name.name.notVerified",
                    null,
                    request.getLocale()));
        }

        if (executionResult == 1) {
            sessionMethods.updateName(request, name);

            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage(
                    "userInformationUpdated",
                    null,
                    request.getLocale()));
        } else {
            throw new SQLException();
        }

        return simpleResponse;
    }

    @PostMapping("/update-pw")
    public SimpleResponse updatePw(HttpServletRequest request,
                                   @SessionAttribute LoginMemberDto loginMember, String pw) throws SQLException {
        int executionResult = 0;
        String verifiedPw = (String) sessionMethods.getSessionAttribute(request, StringConst.VERIFIED_PW);

        SimpleResponse simpleResponse = new SimpleResponse();

        if (verifiedPw.equals(pw)) {
            executionResult = userService.updatePw(loginMember.getUserId(), pw);
        } else {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage(
                    "pw.pw.notVerified",
                    null,
                    request.getLocale()));
        }

        if (executionResult == 1) {
            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage(
                    "userInformationUpdated",
                    null,
                    request.getLocale()));
        } else {
            throw new SQLException();
        }
        return simpleResponse;
    }

    @PostMapping("/update-profile-image")
    public SimpleResponse updateProfileImage(HttpServletRequest request,
                                             @SessionAttribute LoginMemberDto loginMember, MultipartFile multipartFile) throws IOException, SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        fileService.updateProfileImage(loginMember.getUserId(), multipartFile);
        sessionMethods.updateProfileImage(request, fileService.getProfileImageByUserId(loginMember.getUserId()));

        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage(
                "userInformationUpdated",
                null,
                request.getLocale()));

        return simpleResponse;
    }


    @GetMapping("/deactivate-account")
    public void deactivateAccount(HttpServletRequest request, @SessionAttribute LoginMemberDto loginMember) {
        userService.deactivateAccount(loginMember.getUserId(), loginMember.getSavedFileName(), loginMember.getExt());

        sessionMethods.removerAttribute(request, StringConst.LOGIN_MEMBER);
    }
}

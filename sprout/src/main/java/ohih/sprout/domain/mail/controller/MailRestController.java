package ohih.sprout.domain.mail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.mail.service.MailService;
import ohih.sprout.domain.user.dto.Email;
import ohih.sprout.session.SessionMethods;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")

public class MailRestController {

    private final MessageSource messageSource;
    private final SessionMethods sessionMethods;
    private final MailService mailService;


    @PostMapping("/send-code")
    public void sendCode(HttpServletRequest request,
                         @Validated Email email, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            sessionMethods.setVerificationCode(
                    request,
                    mailService.sendCode(email.getAddress()),
                    email.getAddress());
        }
    }

    @PostMapping("/verify-code")
    public SimpleResponse verifyCode(HttpServletRequest request,
                                     String input) {
        String code = (String) sessionMethods.getSessionAttribute(request, StringConst.VERIFICATION_CODE);
        String address = (String) sessionMethods.getSessionAttribute(request, code);

        SimpleResponse simpleResponse = new SimpleResponse();

        if (input.equals(code)) {
            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage(
                    "email.address.verified",
                    null,
                    request.getLocale()));

            sessionMethods.removerAttribute(request, StringConst.VERIFICATION_CODE);
            sessionMethods.setSessionAttribute(request, StringConst.VERIFIED_EMAIL, address);

        } else {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage(
                    "code.incorrect",
                    null,
                    request.getLocale()));
        }

        return simpleResponse;
    }
}

package ohih.sprout.domain.mail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.*;
import ohih.sprout.domain.user.service.UserService;
import ohih.sprout.exception.customException.ValueDuplicatedException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final SproutMail sproutMail;
    private final JavaMailSender javaMailSender;


    private final UserService userService;


    private String createCode() {
        return Utilities.createUUID().substring(0, 5);
    }


    // email code
    public String sendCode(String address) {
        if (userService.checkEmailDuplicated(address)) {
            throw new ValueDuplicatedException(StringConst.EMAIL, StringConst.ADDRESS, address);
        } else {
            String code = createCode();

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sproutMail.getFrom());
            simpleMailMessage.setTo(address);
            simpleMailMessage.setSubject(sproutMail.getSubject());
            simpleMailMessage.setText(code);

            javaMailSender.send(simpleMailMessage);

            log.info("code = {}", code);
            return code;
        }
    }
}

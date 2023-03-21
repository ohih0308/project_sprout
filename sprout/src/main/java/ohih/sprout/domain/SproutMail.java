package ohih.sprout.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SproutMail {

    @Value("#{userEmailVerification['from']}")
    private String from;
    @Value("#{userEmailVerification['subject']}")
    private String subject;
    @Value("#{userEmailVerification['text']}")
    private String text;
}

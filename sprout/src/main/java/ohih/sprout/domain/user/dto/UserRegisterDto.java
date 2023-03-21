package ohih.sprout.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class UserRegisterDto {

    private Long userId;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9+]{4,12}$")
    private String pw;

    private String pwConfirmation;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣+]{4,12}$")
    private String name;
}

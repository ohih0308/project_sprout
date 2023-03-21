package ohih.sprout.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class Pw {

    @Pattern(regexp = "^[a-zA-Z0-9+]{4,12}$")
    private String pw;
    private String confirmation;
}

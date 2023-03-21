package ohih.sprout.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class Name {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣+]{4,12}$")
    private String name;
}

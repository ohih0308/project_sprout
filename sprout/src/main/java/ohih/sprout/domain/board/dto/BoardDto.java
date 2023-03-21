package ohih.sprout.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class BoardDto {

    @Pattern(regexp = "^[a-zA-Z0-9+]{2,30}$")
    private String categoryName;
    @Pattern(regexp = "^[a-zA-Z0-9+]{2,30}$")
    private String boardName;
}

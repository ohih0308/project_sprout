package ohih.sprout.domain.myPage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountsByBoardNameDto {

    private String boardName;
    private int cnt;
}

package ohih.sprout.domain.diary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryConfigUpdateDto {

    private Long userId;
    private Boolean privateRead;
    private Boolean privateWrite;

    private Boolean diaryActivation;
}

package ohih.sprout.domain.diary.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryAccessAuthorityDto {

    private Long diaryId;
    private byte userType;
    private Long userId;
    private String author;
    private String pw;
}

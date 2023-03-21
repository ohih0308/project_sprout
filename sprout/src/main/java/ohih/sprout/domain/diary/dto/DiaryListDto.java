package ohih.sprout.domain.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DiaryListDto {

    private Long owner;

    private byte userType;
    private Long userId;
    private String userIp;
    private String author;


    private Long postId;
    private Boolean privatePost;
    private String text;
    private Date regDate;
}

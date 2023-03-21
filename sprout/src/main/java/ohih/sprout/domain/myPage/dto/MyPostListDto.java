package ohih.sprout.domain.myPage.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyPostListDto {

    private String boardName;
    private Long postId;
    private String subject;
    private Long views;
    private Date regDate;

    private Integer commentCount;
}

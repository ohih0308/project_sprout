package ohih.sprout.domain.myPage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyCommentListDto {

    private Long commentId;
    private Long postId;
    private String text;

    private String boardName;

    private Date regDate;
}

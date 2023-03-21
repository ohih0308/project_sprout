package ohih.sprout.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentListDto {

    private Long rowNum;
    private Long commentId;

    private byte userType;
    private Long userId;
    private String userIp;
    private String author;
//    private String pw;

    private String savedFileName;
    private String ext;

    private Long postId;
    private String text;
    private Date regDate;
}

package ohih.sprout.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAccessAuthority {

    private Long commentId;
    private byte userType;
    private Long userId;
    private String author;
    private String pw;
}

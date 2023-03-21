package ohih.sprout.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAccessAuthorityDto {

    private Long postId;
    private byte userType;
    private Long userId;
    private String author;
    private String pw;
}

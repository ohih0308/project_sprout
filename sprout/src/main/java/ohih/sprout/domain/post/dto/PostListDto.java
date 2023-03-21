package ohih.sprout.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class PostListDto {

    private Long postId;

    private byte userType;
    private String userIp;
    private String author;

    private String savedFileName;
    private String ext;

    private String subject;
    private Long views;
    private Date regDate;
}

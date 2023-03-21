package ohih.sprout.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {

    private Long postId;

    private byte userType;
    private Long userId;
    private String userIp;
    private String author;

    private String savedFileName;
    private String ext;

    private String boardName;
    private String subject;
    private String text;
    private Long views;
    private Date regDate;
    private Date modDate;

    private int cnt;
}

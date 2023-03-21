package ohih.sprout.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CurrentPostDto {
    private Long postId;
    private String userIp;
    private String subject;
    private String author;
    private String text;
    private Date regDate;
    private String savedFileName;
    private String ext;
}

package ohih.sprout.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
public class CommentWriteDto {

    private Long commentId;

    private byte userType;
    private Long userId;
    private String userIp;
    @Pattern(regexp = "^[a-zA-Z0-9가-힣+]{4,12}$")
    private String author;
    @Pattern(regexp = "^[a-zA-Z0-9+]{4,12}$")
    private String pw;

    private Long postId;
    @Size(min = 5, max = 1000)
    private String text;
    private Date regDate;
}

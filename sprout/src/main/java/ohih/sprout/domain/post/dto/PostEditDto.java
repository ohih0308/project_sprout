package ohih.sprout.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class PostEditDto {

    private Long postId;

    private byte userType;
    private Long userId;
    private String userIp;
    @Pattern(regexp = "^[a-zA-Z0-9가-힣+]{4,12}$")
    private String author;
    @Pattern(regexp = "^[a-zA-Z0-9+]{4,12}$")
    private String pw;

//    @Pattern(regexp = "^[a-zA-Z0-9+]{4,30}$")
    private String boardName;
    @Size(min = 5, max = 1000)
    private String subject;
    @Size(min = 5, max = 10000)
    private String text;
}

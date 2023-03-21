package ohih.sprout.domain.diary.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class DiaryEditDto {

    // diary owner
    private Long owner;


    // visitor
    private byte userType;
    private Long userId;
    private String userIp;


    @Pattern(regexp = "^[a-zA-Z0-9가-힣+]{4,12}$")
    private String author;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣+]{4,12}$")
    private String pw;


    // post info
    private Long postId;
    private Boolean privatePost = false;

    @Size(min = 5, max = 1000)
    private String text;
}

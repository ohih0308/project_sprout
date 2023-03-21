package ohih.sprout.domain.myPage.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserInfoDto {
    private Long userId;
    private byte userType;
    private String email;
    private String name;
    private String savedFileName;
    private String ext;
    private Date regDate;
}

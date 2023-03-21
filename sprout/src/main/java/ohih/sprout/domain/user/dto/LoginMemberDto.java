package ohih.sprout.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class LoginMemberDto {

    private Long userId;
    private byte userType;
    private String email;
    private String name;
    private String savedFileName;
    private String ext;
}

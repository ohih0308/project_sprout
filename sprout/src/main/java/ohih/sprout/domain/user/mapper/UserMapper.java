package ohih.sprout.domain.user.mapper;

import ohih.sprout.domain.user.dto.LoginFormDto;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.domain.user.dto.UserRegisterDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {

    int checkEmailDuplicated(String email);

    int checkNameDuplicated(String name);

    int register(UserRegisterDto userRegisterDto);

    LoginMemberDto login(LoginFormDto loginForm);

    int updateName(Map map);

    int updatePw(Map map);

    Long getUserIdByName(String name);

    void deactivateAccount(Long userId);
}

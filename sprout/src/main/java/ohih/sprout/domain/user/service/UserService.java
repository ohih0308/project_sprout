package ohih.sprout.domain.user.service;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.diary.mapper.DiaryMapper;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.user.dto.LoginFormDto;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.domain.user.dto.UserRegisterDto;
import ohih.sprout.domain.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final DiaryMapper diaryMapper;
    private final FileService fileService;


    public Boolean checkEmailDuplicated(String email) {
        if (userMapper.checkEmailDuplicated(email) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkNameDuplicated(String name) {
        if (userMapper.checkNameDuplicated(name) == 0) {
            return false;
        } else {
            return true;
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDto userRegisterDto) {
        userMapper.register(userRegisterDto);
        diaryMapper.diaryConfigInit(userRegisterDto.getUserId());
    }


    public LoginMemberDto login(LoginFormDto loginFormDto) {
        return userMapper.login(loginFormDto);
    }


    public int updateName(Long userId, String name) {
        Map map = new HashMap();

        map.put(StringConst.NAME, name);
        map.put(StringConst.USER_ID, userId);

        return userMapper.updateName(map);
    }

    public int updatePw(Long userId, String pw) {
        Map map = new HashMap();

        map.put(StringConst.USER_ID, userId);
        map.put(StringConst.PW, pw);

        return userMapper.updatePw(map);
    }

    public Long getUserIdByName(String name) {
        return userMapper.getUserIdByName(name);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deactivateAccount(Long userId, String savedFileName, String ext) {
        userMapper.deactivateAccount(userId);
        fileService.deleteProfileImage(userId, savedFileName, ext);
    }
}

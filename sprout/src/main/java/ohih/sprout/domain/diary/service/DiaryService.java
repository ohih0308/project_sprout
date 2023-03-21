package ohih.sprout.domain.diary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Paging;
import ohih.sprout.domain.Search;
import ohih.sprout.domain.diary.dto.DiaryAccessAuthorityDto;
import ohih.sprout.domain.diary.dto.DiaryConfigUpdateDto;
import ohih.sprout.domain.diary.dto.DiaryListDto;
import ohih.sprout.domain.diary.dto.DiaryEditDto;
import ohih.sprout.domain.diary.mapper.DiaryMapper;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;


    public void activateDiary(Long userId) throws SQLException {
        if (diaryMapper.activateDiary(userId) != 1) {
            throw new SQLException();
        }
    }

    public void deactivateDiary(Long userId) throws SQLException {
        if (diaryMapper.deactivateDiary(userId) != 1) {
            throw new SQLException();
        }
    }

    public void updateDiaryConfig(Long userId, DiaryConfigUpdateDto diaryConfigUpdateDto) throws SQLException {
        diaryConfigUpdateDto.setUserId(userId);

        if (diaryMapper.updateDiaryConfig(diaryConfigUpdateDto) != 1) {
            throw new SQLException();
        }
    }

    /*public Long getTotalCountByOwner(Long userId, Search search) {
        Map map = new HashMap();
        map.put(StringConst.USER_ID, userId);
        map.put(StringConst.SEARCH, search);

        return diaryMapper.getTotalCountByOwner(map);
    }

    public List<DiaryListDto> getPostListByOwner(Long owner, Long userId, Paging paging, Search search) {
        Map map = new HashMap();

        if (userId == null || owner != userId) {
            map.put(StringConst.PRINCIPAL, false);
        }

        map.put(StringConst.OWNER, owner);
        map.put(StringConst.PAGING, paging);
        map.put(StringConst.SEARCH, search);

        return diaryMapper.getPostListByOwner(map);
    }*/

    public void writeDiary(String userIp, DiaryEditDto diaryEditDto) throws SQLException {
        diaryEditDto.setUserIp(userIp);
        diaryEditDto.setUserType(IntegerConst.GUEST);

        if (diaryMapper.writeDiary(diaryEditDto) != 1) {
            throw new SQLException();
        }
    }

    public void writeDiary(String userIp, Long userId, String name, DiaryEditDto diaryEditDto) throws SQLException {
        diaryEditDto.setUserIp(userIp);
        diaryEditDto.setUserType(IntegerConst.MEMBER);
        diaryEditDto.setUserId(userId);
        diaryEditDto.setAuthor(name);

        if (diaryMapper.writeDiary(diaryEditDto) != 1) {
            throw new SQLException();
        }
    }


    public Boolean checkPostAccessAuthority(Long diaryId, String pw) {
        DiaryAccessAuthorityDto commentAccessAuthority = diaryMapper.getDiaryAccessAuthorityDto(diaryId);

        if (commentAccessAuthority.getUserType() == IntegerConst.GUEST) {
            return commentAccessAuthority.getPw().equals(pw);
        } else {
            return false;
        }
    }

    public Boolean checkPostAccessAuthority(Long diaryId, Long userId) {
        DiaryAccessAuthorityDto commentAccessAuthority = diaryMapper.getDiaryAccessAuthorityDto(diaryId);

        if (commentAccessAuthority.getUserType() == IntegerConst.MEMBER) {
            return commentAccessAuthority.getUserId().equals(userId);
        } else {
            return false;
        }
    }

    public Boolean getDiaryActivationStatusByUserId(Long userId) {
        return diaryMapper.getDiaryActivationStatusByUserId(userId);
    }

    public void deleteDiaryByDiaryId(Long diaryId) throws SQLException {
        if (diaryMapper.deleteDiaryByDiaryId(diaryId) != 1) {
            throw new SQLException();
        }
    }

    public Long getTotalDiaryCountByName(String owner) {
        return diaryMapper.getTotalDiaryCountByName(owner);
    }

    public int getTodayDiaryCountByName(String owner) {
        return diaryMapper.getTodayDiaryCountByName(owner);
    }

    public List<DiaryListDto> getDiaryListByName(String owner, Paging paging) {
        Map map = new HashMap();
        map.put(StringConst.OWNER, owner);
        map.put(StringConst.PAGING, paging);

        return diaryMapper.getDiaryListByName(map);
    }

    public List<DiaryListDto> filterDiaryList(String owner, LoginMemberDto loginMember, List<DiaryListDto> totalList) {
        if (loginMember == null || !loginMember.getName().equals(owner)) {
            ArrayList<DiaryListDto> result = new ArrayList<>();

            for (DiaryListDto diaryDto : totalList) {
                if (diaryDto.getPrivatePost() == true) {
                    diaryDto.setText(null);
                }
                result.add(diaryDto);
            }

            return result;
        } else {
            return totalList;
        }
    }
}

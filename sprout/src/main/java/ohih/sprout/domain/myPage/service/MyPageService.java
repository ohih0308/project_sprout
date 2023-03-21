package ohih.sprout.domain.myPage.service;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.Paging;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.diary.mapper.DiaryMapper;
import ohih.sprout.domain.myPage.dto.CountsByBoardNameDto;
import ohih.sprout.domain.myPage.dto.MyCommentListDto;
import ohih.sprout.domain.myPage.dto.MyPostListDto;
import ohih.sprout.domain.myPage.dto.UserInfoDto;
import ohih.sprout.domain.myPage.mapper.MyPageMapper;
import ohih.sprout.domain.post.dto.PostListDto;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ohih.sprout.domain.StringConst.USER_ID;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageMapper myPageMapper;
    private final DiaryMapper diaryMapper;

    public List<MyPostListDto> getPostListByUserId(Long userId) {
        Map map = new HashMap();
        map.put(USER_ID, userId);
        map.put(StringConst.MY_POST_LIST_SIZE, IntegerConst.MY_POST_LIST_SIZE);

        return myPageMapper.getPostListByUserId(map);
    }

    public int getPostCountByUserId(Long userId) {
        return myPageMapper.getPostCountByUserId(userId);
    }

    public int getCommentCountByUserId(Long userId) {
        return myPageMapper.getCommentCountByUserId(userId);
    }

    public List<MyCommentListDto> getCommentListByUserId(Long userId) {
        Map map = new HashMap();
        map.put(StringConst.USER_ID, userId);
        map.put(StringConst.MY_COMMENT_LIST_SIZE, IntegerConst.MY_COMMENT_LIST_SIZE);

        return myPageMapper.getCommentListByUserId(map);
    }

    public Boolean getDiaryActivationByUserId(Long userId) {
        return myPageMapper.getDiaryActivationByUserId(userId);
    }


    // post record
    public Long getTotalCountOfPostByName(String name) {
        return myPageMapper.getTotalCountOfPostByName(name);
    }

    public List<MyPostListDto> getPostListByName(String name, Paging paging) {
        Map map = new HashMap();
        map.put(StringConst.NAME, name);
        map.put(StringConst.PAGING, paging);

        return myPageMapper.getPostListByName(map);
    }

    public List<CountsByBoardNameDto> getCountsByBoardNamePost(String name) {
        return myPageMapper.getCountsByBoardNamePost(name);
    }

    public Long getPostCountByAuthorAndBoardName(String boardName, String author) {
        Map map = new HashMap();
        map.put(StringConst.BOARD_NAME, boardName);
        map.put(StringConst.AUTHOR, author);

        return myPageMapper.getPostCountByAuthorAndBoardName(map);
    }

    public List<MyPostListDto> getPostListByAuthorAndBoardName(String boardName, String author, Paging paging) {
        Map map = new HashMap();
        map.put(StringConst.BOARD_NAME, boardName);
        map.put(StringConst.AUTHOR, author);
        map.put(StringConst.PAGING, paging);

        return myPageMapper.getPostListByAuthorAndBoardName(map);
    }


    // comment record
    public Long getTotalCountOfCommentByName(String name) {
        return myPageMapper.getTotalCountOfCommentByName(name);
    }

    public List<CountsByBoardNameDto> getCountsByBoardNameComment(String name) {
        return myPageMapper.getCountsByBoardNameComment(name);
    }

    public Long getCommentCountByAuthorAndBoardName(String boardName, String author) {
        Map map = new HashMap();
        map.put(StringConst.BOARD_NAME, boardName);
        map.put(StringConst.AUTHOR, author);

        return myPageMapper.getCommentCountByAuthorAndBoardName(map);
    }

    public List<MyCommentListDto> getCommentListByAuthorAndBoardName(String boardName, String author, Paging paging) {
        Map map = new HashMap();
        map.put(StringConst.BOARD_NAME, boardName);
        map.put(StringConst.AUTHOR, author);
        map.put(StringConst.PAGING, paging);

        return myPageMapper.getCommentListByAuthorAndBoardName(map);
    }

    public UserInfoDto getUserInfoByName(String name) {
        return myPageMapper.getUserInfoByName(name);
    }

    public Boolean getDiaryActivationStatus(String name) {
        return diaryMapper.getDiaryActivationStatus(name);
    }
}

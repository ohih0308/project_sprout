package ohih.sprout.domain.myPage.mapper;

import ohih.sprout.domain.myPage.dto.CountsByBoardNameDto;
import ohih.sprout.domain.myPage.dto.MyCommentListDto;
import ohih.sprout.domain.myPage.dto.MyPostListDto;
import ohih.sprout.domain.myPage.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageMapper {

    List<MyPostListDto> getPostListByUserId(Map map);

    List<MyCommentListDto> getCommentListByUserId(Map map);

    int getPostCountByUserId(Long userId);

    int getCommentCountByUserId(Long userId);

    Boolean getDiaryActivationByUserId(Long userId);

    Long getTotalCountOfPostByName(String name);

    List<MyPostListDto> getPostListByName(Map map);

    List<CountsByBoardNameDto> getCountsByBoardNamePost(String name);

    // post record
    Long getPostCountByAuthorAndBoardName(Map map);

    List<MyPostListDto> getPostListByAuthorAndBoardName(Map map);


    // comment record
    Long getTotalCountOfCommentByName(String name);

    List<CountsByBoardNameDto> getCountsByBoardNameComment(String name);

    Long getCommentCountByAuthorAndBoardName(Map map);

    List<MyCommentListDto> getCommentListByAuthorAndBoardName(Map map);

    UserInfoDto getUserInfoByName(String name);
}

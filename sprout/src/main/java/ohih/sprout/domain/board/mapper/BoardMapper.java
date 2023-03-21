package ohih.sprout.domain.board.mapper;

import ohih.sprout.domain.board.dto.BoardDto;
import ohih.sprout.domain.post.dto.PostListDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    List<BoardDto> getBoardList();

    Long getTotalCountByBoard(Map map);

    List<PostListDto> getPostListByBoard(Map mpa);

//    List<PostListDto> getPostsOfTheDay();


//    List<BoardDto> getBoardListByCategoryName(String categoryName);

    int createBoard(BoardDto boardDto);

    int checkBoardDuplicated(String boardName);

    int deleteBoard(String boardName);
}

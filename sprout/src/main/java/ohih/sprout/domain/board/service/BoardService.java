package ohih.sprout.domain.board.service;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Paging;
import ohih.sprout.domain.Search;
import ohih.sprout.domain.board.dto.BoardDto;
import ohih.sprout.domain.category.dto.CategoryDto;
import ohih.sprout.domain.post.dto.PostListDto;
import ohih.sprout.domain.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;


    public List<BoardDto> getBoardList() {
        return boardMapper.getBoardList();
    }

    public Long getTotalCountByBoard(String board, Search search) {
        Map map = new HashMap();
        map.put(StringConst.BOARD_NAME, board);
        map.put(StringConst.SEARCH, search);

        return boardMapper.getTotalCountByBoard(map);
    }

    public List<PostListDto> getPostListByBoard(String boardName, Paging paging, Search search) {
        Map map = new HashMap();

        map.put(StringConst.BOARD_NAME, boardName);
        map.put(StringConst.PAGING, paging);
        map.put(StringConst.SEARCH, search);

        return boardMapper.getPostListByBoard(map);
    }

//    public List<PostListDto> getPostsOfTheDay() {
//        return null;
//    }


//    public List<BoardDto> getBoardListByCategoryName(String categoryName) {
//        return boardMapper.getBoardListByCategoryName(categoryName);
//    }

    public void createBoard(BoardDto boardDto) throws SQLException {
        if (boardMapper.createBoard(boardDto) != 1) {
            throw new SQLException();
        }
    }

    public Boolean checkBoardDuplicated(String boardName) {
        if (boardMapper.checkBoardDuplicated(boardName) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteBoard(String boardName) throws SQLException {
        if (boardMapper.deleteBoard(boardName) != 1) {
            throw new SQLException();
        }
    }
}

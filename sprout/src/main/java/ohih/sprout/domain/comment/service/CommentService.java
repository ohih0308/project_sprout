package ohih.sprout.domain.comment.service;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Paging;
import ohih.sprout.domain.comment.dto.CommentAccessAuthority;
import ohih.sprout.domain.comment.dto.CommentListDto;
import ohih.sprout.domain.comment.dto.CommentWriteDto;
import ohih.sprout.domain.comment.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;


    public Long getTotalCountByPostId(Long postId) {
        return commentMapper.getTotalCountByPostId(postId);
    }

    public List<CommentListDto> getCommentListByPostId(Long postId, Paging paging) {
        Map map = new HashMap();
        map.put(StringConst.POST_ID, postId);
        map.put(StringConst.PAGING, paging);

        return commentMapper.getCommentByPostId(map);
    }

    public void writeComment(CommentWriteDto commentWriteDto) throws SQLException {
        commentWriteDto.setUserType(IntegerConst.GUEST);

        if (commentMapper.writeComment(commentWriteDto) != 1) {
            throw new SQLException();
        }
    }

    public void writeComment(Long userId,String name, CommentWriteDto commentWriteDto) throws SQLException {
        commentWriteDto.setUserType(IntegerConst.MEMBER);
        commentWriteDto.setUserId(userId);
        commentWriteDto.setAuthor(name);

        if (commentMapper.writeComment(commentWriteDto) != 1) {
            throw new SQLException();
        }
    }


    public Boolean checkCommentAccessAuthority(Long commentId, String pw) {
        CommentAccessAuthority commentAccessAuthority = commentMapper.getCommentAccessAuthority(commentId);

        if (commentAccessAuthority.getUserType() == IntegerConst.GUEST) {
            return commentAccessAuthority.getPw().equals(pw);
        } else {
            return false;
        }
    }

    public Boolean checkCommentAccessAuthority(Long commentId, Long userId) {
        CommentAccessAuthority commentAccessAuthority = commentMapper.getCommentAccessAuthority(commentId);

        if (commentAccessAuthority.getUserType() == IntegerConst.MEMBER) {
            return commentAccessAuthority.getUserId().equals(userId);
        } else {
            return false;
        }
    }

    public void deleteCommentByCommentId(Long commentId) throws SQLException {
        if (commentMapper.deleteCommentByCommentId(commentId) != 1) {
            throw new SQLException();
        }
    }

    public int deleteCommentByPostId(Long postId) {
        return commentMapper.deleteCommentByPostId(postId);
    }

    public int getCommentCountByPostId(Long postId) {
        return commentMapper.getCommentCountByPostId(postId);
    }

    public int getCommentCountByUserId(Long userId) {
        return commentMapper.getCommentCountByUserId(userId);
    }
}

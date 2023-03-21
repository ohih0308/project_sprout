package ohih.sprout.domain.comment.mapper;

import ohih.sprout.domain.comment.dto.CommentAccessAuthority;
import ohih.sprout.domain.comment.dto.CommentListDto;
import ohih.sprout.domain.comment.dto.CommentWriteDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {

    List<CommentListDto> getCommentByPostId(Map map);

    Long getTotalCountByPostId(Long postId);

    int writeComment(CommentWriteDto commentWriteDto);

    CommentAccessAuthority getCommentAccessAuthority(Long commentId);

    int deleteCommentByCommentId(Long commentId);

    int deleteCommentByPostId(Long postId);

    Integer getCommentCountByPostId(Long postId);


    int getCommentCountByUserId(Long userId);
}

package ohih.sprout.domain.post.mapper;

import ohih.sprout.domain.post.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostMapper {

    PostDto getPostDtoByPostId(Long postId);

    int writePost(PostEditDto postEditDto);

    PostAccessAuthorityDto getPostAccessAuthorityDto(Long postId);

    PostEditDto getPostEditDtoByPostId(Long postId);

    int updatePost(PostEditDto postEditDto);

    int deletePostByPostId(Long postId);


    int getPostCountByUserId(Long userId);


    int addViewCount(Long postId);


    int getReputationCountByPostId(ReputationDto reputationDto);

    int addReputation(ReputationDto reputationDto);

    int updateReputation(ReputationDto reputationDto);

    ReputationCountDto getReputationCountByType(Long postId);

//    List<Long> getPostListByBoardName(String boardName);

    List<CurrentPostDto> getCurrentPost();
}

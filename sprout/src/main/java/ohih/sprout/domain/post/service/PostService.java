package ohih.sprout.domain.post.service;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.post.dto.*;
import ohih.sprout.domain.post.mapper.PostMapper;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;


    public PostDto getPostDtoByPostId(Long postId) {
        return postMapper.getPostDtoByPostId(postId);
    }

    public int writePost(LoginMemberDto loginMemberDto, String userIp, PostEditDto postEditDto) {
        postEditDto.setUserIp(userIp);

        if (loginMemberDto == null) {
            postEditDto.setUserType(IntegerConst.GUEST);
        } else {
            postEditDto.setUserType(loginMemberDto.getUserType());
            postEditDto.setUserId(loginMemberDto.getUserId());
            postEditDto.setAuthor(loginMemberDto.getName());
        }

        return postMapper.writePost(postEditDto);
    }

    // none registered
    public Boolean checkPostAccessAuthority(Long postId,
                                            String pw) {
        PostAccessAuthorityDto postAccessAuthorityDto = postMapper.getPostAccessAuthorityDto(postId);

        if (postAccessAuthorityDto.getUserType() == IntegerConst.GUEST) {
            return postAccessAuthorityDto.getPw().equals(pw);
        } else {
            return false;
        }
    }

    // member
    public Boolean checkPostAccessAuthority(Long postId,
                                            Long userId) {
        System.out.println("postId = " + postId);
        System.out.println("userId = " + userId);

        PostAccessAuthorityDto postAccessAuthorityDto = postMapper.getPostAccessAuthorityDto(postId);

        System.out.println("postAccessAuthorityDto = " + postAccessAuthorityDto);

        if (postAccessAuthorityDto.getUserType() == IntegerConst.MEMBER) {
            return postAccessAuthorityDto.getUserId().equals(userId);
        } else {
            return false;
        }
    }


    public PostEditDto getPostEditDtoByPostId(Long postId) {
        return postMapper.getPostEditDtoByPostId(postId);
    }

    public void updatePost(String ip, Long postId, PostEditDto postEditDto) throws SQLException {
        PostAccessAuthorityDto postAccessAuthorityDto = postMapper.getPostAccessAuthorityDto(postId);

        postEditDto.setUserIp(ip);

        if (postAccessAuthorityDto.getUserType() != IntegerConst.GUEST) {
            postEditDto.setAuthor(postAccessAuthorityDto.getAuthor());
        }

        if (postMapper.updatePost(postEditDto) != 1) {
            throw new SQLException();
        }
    }

    public int deletePostByPostId(Long postId) {
        return postMapper.deletePostByPostId(postId);
    }


    public int getPostCountByUserId(Long userId) {
        return postMapper.getPostCountByUserId(userId);
    }

    public void addViewCount(Long postId) throws SQLException {
        if (postMapper.addViewCount(postId) != 1) {
            throw new SQLException();
        }

    }


    private int getReputationCountByPostId(ReputationDto reputationDto) {
        return postMapper.getReputationCountByPostId(reputationDto);
    }

    public void setReputation(ReputationDto reputationDto) throws SQLException {
        int queryExecutionResult = 0;
        int reputationCount = getReputationCountByPostId(reputationDto);

        if (reputationCount == 0) {
            queryExecutionResult = postMapper.addReputation(reputationDto);
        } else {
            queryExecutionResult = postMapper.updateReputation(reputationDto);
        }


        if (queryExecutionResult != 1) {
            throw new SQLException();
        }
    }

    public ReputationCountDto getReputationCountByType(Long postId) {
        return postMapper.getReputationCountByType(postId);
    }

//    public List<Long> getPostListByBoardName(String boardName) {
//        return postMapper.getPostListByBoardName(boardName);
//    }

    public List<CurrentPostDto> getCurrentPost() {
        return postMapper.getCurrentPost();
    }
}

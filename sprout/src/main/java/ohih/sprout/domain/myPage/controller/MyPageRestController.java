package ohih.sprout.domain.myPage.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.*;
import ohih.sprout.domain.comment.service.CommentService;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.myPage.dto.MyPostListDto;
import ohih.sprout.domain.myPage.service.MyPageService;
import ohih.sprout.domain.post.service.PostService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/my-page/")
@RequiredArgsConstructor
public class MyPageRestController {


    private final MessageSource messageSource;
    private final MyPageService myPageService;
    private final FileService fileService;
    private final CommentService commentService;
    private final PostService postService;


    @PostMapping("/post-record")
    public Map getPostRecord(String name, Paging paging, String boardName) {
        Long totalCount = myPageService.getPostCountByAuthorAndBoardName(boardName, name);
        paging = Utilities.getPaging(totalCount, paging, IntegerConst.MY_POST_LIST_SIZE);

        Map map = new HashMap();
        map.put(StringConst.PAGING, paging);
        map.put(StringConst.POST_LIST, myPageService.getPostListByAuthorAndBoardName(boardName, name, paging));

        return map;
    }

    @PostMapping("/post-record/delete")
    public SimpleResponse deletePostRecord(@SessionAttribute LoginMemberDto loginMember, HttpServletRequest request,
                                           @Nullable @RequestParam("selectedPostIdList") List<Long> selectedPostIdList)
            throws IOException, SQLException {
        int attachedFileCount;
        int commentCount;

        SimpleResponse simpleResponse = new SimpleResponse();

        if (loginMember == null) {
            simpleResponse.setResult(false);
        }

        for (Long postId : selectedPostIdList) {
            // check post access qualification as postId
            if (!postService.checkPostAccessAuthority(postId, loginMember.getUserId())) {
                simpleResponse.setResult(false);
                break;
            }

            attachedFileCount = fileService.getAttachedFileCountByPostId(postId);
            commentCount = commentService.getCommentCountByPostId(postId);

            if (attachedFileCount > 0
                    && attachedFileCount != fileService.deleteAttachedFilesByPostId(postId)) {
                throw new IOException();
            }

            if (commentCount > 0
                    && commentCount != commentService.deleteCommentByPostId(postId)) {
                throw new SQLException();
            }

            if (postService.deletePostByPostId(postId) == 1) {
                simpleResponse.setResult(true);
            } else {
                throw new SQLException();
            }
        }


        if (simpleResponse.getResult()) {
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteMyPosts",
                    null,
                    request.getLocale()
            ));
        } else {
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteMyPostsFailed",
                    null,
                    request.getLocale()
            ));
        }

        return simpleResponse;
    }

    @PostMapping("/comment-record")
    public Map getCommentRecord(String name, Paging paging, String boardName) {
        Long totalCount = myPageService.getCommentCountByAuthorAndBoardName(boardName, name);
        paging = Utilities.getPaging(totalCount, paging, IntegerConst.MY_COMMENT_LIST_SIZE);

        Map map = new HashMap();
        map.put(StringConst.PAGING, paging);
        map.put(StringConst.COMMENT_LIST, myPageService.getCommentListByAuthorAndBoardName(boardName, name, paging));

        return map;
    }

    @PostMapping("/comment-record/delete")
    public SimpleResponse deleteCommentRecord(@SessionAttribute LoginMemberDto loginMember, HttpServletRequest request,
                                              @Nullable @RequestParam("selectedCommentIdList") List<Long> selectedCommentIdList) throws SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        if (loginMember == null) {
            simpleResponse.setResult(false);
        }

        for (Long commentId : selectedCommentIdList) {
            // check comment access qualification as commentId
            if (!commentService.checkCommentAccessAuthority(commentId, loginMember.getUserId())) {
                simpleResponse.setResult(false);
                break;
            }

            commentService.deleteCommentByCommentId(commentId);

            simpleResponse.setResult(true);
        }

        if (simpleResponse.getResult()) {
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteMyComments",
                    null,
                    request.getLocale()
            ));
        } else {
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteMyCommentsFailed",
                    null,
                    request.getLocale()
            ));
        }

        return simpleResponse;
    }
}

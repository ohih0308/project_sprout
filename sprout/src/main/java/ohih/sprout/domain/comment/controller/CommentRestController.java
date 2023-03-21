package ohih.sprout.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.*;
import ohih.sprout.domain.comment.dto.CommentWriteDto;
import ohih.sprout.domain.comment.service.CommentService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ohih.sprout.domain.Utilities.getPaging;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment/")
public class CommentRestController {

    private final MessageSource messageSource;

    private final CommentService commentService;


    @PostMapping("/{postId}/list")
    public Map getCommentListByPostId(Model model,
                                      @PathVariable Long postId, Paging paging) {
        Long totalCountByPostId = commentService.getTotalCountByPostId(postId);
        paging = getPaging(totalCountByPostId, paging, IntegerConst.COMMENT_LIST_SIZE);
        model.addAttribute(StringConst.PAGING, paging);
        Map map = new HashMap();
        map.put(StringConst.PAGING, paging);
        map.put(StringConst.COMMENT_LIST, commentService.getCommentListByPostId(postId, paging));

        return map;
    }

    @PostMapping("/write")
    public SimpleResponse writeComment(HttpServletRequest request,
                                       @Nullable @SessionAttribute LoginMemberDto loginMember,
                                       @Validated CommentWriteDto commentWriteDto, BindingResult bindingResult)
            throws BindException, SQLException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        commentWriteDto.setUserIp(Utilities.getIp(request));


        if (loginMember == null) {
            commentService.writeComment(commentWriteDto);
        } else {
            commentService.writeComment(loginMember.getUserId(), loginMember.getName(), commentWriteDto);
        }

        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("writeComment", null, request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/{commentId}/delete")
    public SimpleResponse deleteComment(HttpServletRequest request,
                                        @PathVariable Long commentId,
                                        @Nullable @SessionAttribute LoginMemberDto loginMember,
                                        String pw) throws SQLException {

        SimpleResponse simpleResponse = new SimpleResponse();

        if (loginMember == null && commentService.checkCommentAccessAuthority(commentId, pw)
                || loginMember != null
                && commentService.checkCommentAccessAuthority(commentId, loginMember.getUserId())) {
            commentService.deleteCommentByCommentId(commentId);

            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteComment",
                    null,
                    request.getLocale()
            ));
        } else {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage(
                    "deleteCommentFailure",
                    null,
                    request.getLocale()
            ));
        }

        return simpleResponse;
    }
}

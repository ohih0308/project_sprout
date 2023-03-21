package ohih.sprout.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.Utilities;
import ohih.sprout.domain.comment.service.CommentService;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.post.dto.ReputationDto;
import ohih.sprout.domain.post.service.PostService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostRestController {

    private final MessageSource messageSource;

    private final PostService postService;
    private final FileService fileService;
    private final CommentService commentService;


    @PostMapping("/{postId}/delete")
    public SimpleResponse deletePost(HttpServletRequest request,
                                     @PathVariable Long postId) throws IOException, SQLException {

        int attachedFileCount = fileService.getAttachedFileCountByPostId(postId);
        int commentCount = commentService.getCommentCountByPostId(postId);

        if (attachedFileCount > 0
                && attachedFileCount != fileService.deleteAttachedFilesByPostId(postId)) {
            throw new IOException();
        }

        if (commentCount > 0
                && commentCount != commentService.deleteCommentByPostId(postId)) {
            throw new SQLException();
        }

        SimpleResponse simpleResponse = new SimpleResponse();

        if (postService.deletePostByPostId(postId) == 1) {
            simpleResponse.setResult(true);

            simpleResponse.setMessage(messageSource.getMessage(
                    "deletePost",
                    null,
                    request.getLocale()
            ));
        } else {
            throw new SQLException();
        }

        return simpleResponse;
    }

    @PostMapping("/reputation")
    public void reputation(HttpServletRequest request,
                           ReputationDto reputationDto) throws SQLException {
        reputationDto.setIp(Utilities.getIp(request));

        postService.setReputation(reputationDto);
    }
}

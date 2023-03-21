package ohih.sprout.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Utilities;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.post.dto.PostEditDto;
import ohih.sprout.domain.post.service.PostService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.session.SessionMethods;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final SessionMethods sessionMethods;

    private final PostService postService;
    private final FileService fileService;


    @GetMapping("/{postId}")
    public String getPostByPostId(Model model, @PathVariable Long postId) throws SQLException {
        postService.addViewCount(postId);
        model.addAttribute(StringConst.POST, postService.getPostDtoByPostId(postId));
        model.addAttribute(StringConst.ATTACHED_FILE_LIST, fileService.getAttachedFileListByPostId(postId));
        model.addAttribute(StringConst.REPUTATION, postService.getReputationCountByType(postId));

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/post";
    }

    @GetMapping("/write-form")
    public String writePostForm(@ModelAttribute PostEditDto postEditDto, Model model) {
        model.addAttribute(StringConst.BOARD_NAME, postEditDto.getBoardName());

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/write";
    }

    @PostMapping("/write")
    public String writePost(HttpServletRequest request,
                            Model model,
                            @Nullable @SessionAttribute LoginMemberDto loginMember,
                            @Validated PostEditDto postEditDto, BindingResult bindingResult,
                            List<MultipartFile> multipartFileList) throws IOException, SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute(StringConst.BOARD_NAME, postEditDto.getBoardName());
            return "pages/write";
        }

        if (postService.writePost(loginMember, Utilities.getIp(request), postEditDto) != 1) {
            throw new SQLException();
        }


        if (multipartFileList != null) {
            for (MultipartFile multipartFile : multipartFileList) {
                fileService.saveAttachedFile(postEditDto.getPostId(), postEditDto.getBoardName(), multipartFile);
            }
        }

        model.addAttribute(StringConst.BOARD_NAME, postEditDto.getBoardName());

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "redirect:/board/" + postEditDto.getBoardName();
    }

    @PostMapping("/{postId}/edit-form")
    public String editPostForm(HttpServletRequest request, Model model,
                               @Nullable @SessionAttribute LoginMemberDto loginMember,
                               @PathVariable Long postId,
                               String pw) {
        if (postService.checkPostAccessAuthority(postId, pw)
                || loginMember != null && postService.checkPostAccessAuthority(postId, loginMember.getUserId())) {
            sessionMethods.setSessionAttribute(request, StringConst.ACCESS_AUTHORIZED_POST, postId);

            PostEditDto postEditDto = postService.getPostEditDtoByPostId(postId);

            model.addAttribute(StringConst.PW, pw);
            model.addAttribute(StringConst.POST_EDIT_DTO, postEditDto);
            model.addAttribute(StringConst.ATTACHED_FILE_LIST, fileService.getAttachedFileListByPostId(postId));

            List<AdFileDto> adFileList = fileService.getAdFileList();
            model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
            return "pages/edit";
        } else {
            return "redirect:/post/" + postId;
        }
    }


    @PostMapping("/{postId}/edit")
    public String editPost(HttpServletRequest request, Model model,
                           @PathVariable Long postId,
                           @Validated PostEditDto postEditDto, BindingResult bindingResult,
                           List<MultipartFile> multipartFileList,
                           @Nullable @RequestParam("deleteFileList") ArrayList<String> deleteFileList) throws IOException, SQLException {
        if (bindingResult.hasErrors()) {
            model.addAttribute(StringConst.POST_EDIT_DTO, postService.getPostEditDtoByPostId(postId));
            model.addAttribute(StringConst.ATTACHED_FILE_LIST, fileService.getAttachedFileListByPostId(postId));

            List<AdFileDto> adFileList = fileService.getAdFileList();
            model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

            return "pages/edit";
        }

        postService.updatePost(Utilities.getIp(request), postId, postEditDto);

        if (deleteFileList != null) {
            for (String savedFileName : deleteFileList) {
                fileService.deleteAttachedFile(savedFileName);
            }
        }

        if (multipartFileList != null) {
            for (MultipartFile multipartFile : multipartFileList) {
                fileService.saveAttachedFile(postId, postEditDto.getBoardName(), multipartFile);
            }
        }

        sessionMethods.removerAttribute(request, StringConst.ACCESS_AUTHORIZED_POST);

        return "redirect:/post/" + postId;
    }

    @PostMapping("/{postId}/delete-form")
    public String deletePostForm(HttpServletRequest request,
                                 Model model,
                                 @Nullable @SessionAttribute LoginMemberDto loginMemberDto,
                                 @PathVariable Long postId, String pw, String boardName) {

        if (loginMemberDto == null && postService.checkPostAccessAuthority(postId, pw)
                || loginMemberDto != null && postService.checkPostAccessAuthority(postId, loginMemberDto.getUserId())) {
            model.addAttribute(StringConst.BOARD_NAME, boardName);
            model.addAttribute(StringConst.POST_ID, postId);

            sessionMethods.setSessionAttribute(request, StringConst.ACCESS_AUTHORIZED_POST, postId);

            List<AdFileDto> adFileList = fileService.getAdFileList();
            model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
            return "pages/delete-confirm";
        } else {
            model.addAttribute("post", postService.getPostDtoByPostId(postId));
            return "pages/post";
        }
    }
}

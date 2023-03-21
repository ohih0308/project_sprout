package ohih.sprout.domain.myPage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.board.service.BoardService;
import ohih.sprout.domain.category.service.CategoryService;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.myPage.service.MyPageService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.domain.user.service.UserService;
import ohih.sprout.session.SessionMethods;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my-page/{name}")
@Slf4j
public class MyPageController {
    private final MyPageService myPageService;
    private final UserService userService;
    private final SessionMethods sessionMethods;

    private final CategoryService categoryService;

    private final BoardService boardService;
    private final FileService fileService;


    @GetMapping("")
    public String myPage(Model model, @PathVariable String name) {
        Long userId = userService.getUserIdByName(name);

        model.addAttribute(StringConst.OWNER, name);
        model.addAttribute(StringConst.OWNER_ID, userId);

        model.addAttribute(StringConst.POST_COUNT, myPageService.getPostCountByUserId(userId));
        model.addAttribute(StringConst.POST_LIST, myPageService.getPostListByUserId(userId));

        model.addAttribute(StringConst.COMMENT_COUNT, myPageService.getCommentCountByUserId(userId));
        model.addAttribute(StringConst.COMMENT_LIST, myPageService.getCommentListByUserId(userId));

        model.addAttribute(StringConst.DIARY_ACTIVATION, myPageService.getDiaryActivationByUserId(userId));


        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/my-page";
    }

    // 수정: 위에서는 postId를 사용해 데이터를 찾았지만 아래 메서드에서는 "name"을 사용하였고 쿼리에 "userType"을 조건으로 추가하였다
    @GetMapping("/record/post")
    public String historyPost(Model model,
                              @PathVariable String name) {
        model.addAttribute(StringConst.OWNER, name);
        model.addAttribute(StringConst.POST_COUNT, myPageService.getTotalCountOfPostByName(name));
        model.addAttribute(StringConst.COUNT_BY_BOARD_NAME_POST, myPageService.getCountsByBoardNamePost(name));

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/postRecord";
    }

    @GetMapping("/record/comment")
    public String historyComment(Model model,
                                 @PathVariable String name) {
        model.addAttribute(StringConst.OWNER, name);
        model.addAttribute(StringConst.COMMENT_COUNT, myPageService.getTotalCountOfCommentByName(name));
        model.addAttribute(StringConst.COUNT_BY_BOARD_NAME_Comment, myPageService.getCountsByBoardNameComment(name));

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/commentRecord";
    }

    @GetMapping("/config")
    public String config(Model model,
                         @PathVariable String name, @Nullable @SessionAttribute LoginMemberDto loginMember)
            throws Exception {
        if (loginMember == null) {
            throw new Exception();
        }

        if (loginMember.getUserType() == IntegerConst.ADMIN) {
            return "redirect:/my-page/" + name + "/admin";
        } else {

            model.addAttribute(StringConst.USER_INFO, myPageService.getUserInfoByName(loginMember.getName()));
            model.addAttribute(StringConst.DIARY_ACTIVATION, myPageService.getDiaryActivationStatus(name));

            List<AdFileDto> adFileList = fileService.getAdFileList();
            model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

            return "pages/config";
        }
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, Model model,
                        @PathVariable String name) {
        if (sessionMethods.isAdmin(request)) {
            model.addAttribute(StringConst.OWNER, name);
            model.addAttribute(StringConst.CATEGORY_LIST, categoryService.getCategoryList());
            model.addAttribute(StringConst.BOARD_LIST, boardService.getBoardList());

            model.addAttribute(StringConst.EVENT_FILE_LIST, fileService.getEventFileList());

            List<AdFileDto> adFileList = fileService.getAdFileList();
            model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
            return "pages/admin";
        } else {
            return "pages/home";
        }
    }
}

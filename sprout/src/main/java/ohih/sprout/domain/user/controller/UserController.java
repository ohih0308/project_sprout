package ohih.sprout.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.*;
import ohih.sprout.domain.comment.service.CommentService;
import ohih.sprout.domain.diary.service.DiaryService;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.post.service.PostService;
import ohih.sprout.domain.user.dto.LoginFormDto;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.domain.user.dto.UserRegisterDto;
import ohih.sprout.domain.user.service.UserService;
import ohih.sprout.session.SessionMethods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final SessionMethods sessionMethods;

    private final UserService userService;
    private final FileService fileService;


    @GetMapping("/register")
    public String registerForm(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

        return "pages/register";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request,
                           @ModelAttribute @Validated UserRegisterDto userRegisterDto, BindingResult bindingResult,
                           MultipartFile multipartFile, Model model) throws IOException, SQLException {

        String verifiedEmail = (String) sessionMethods.getSessionAttribute(request, StringConst.VERIFIED_EMAIL);
        String verifiedName = (String) sessionMethods.getSessionAttribute(request, StringConst.VERIFIED_NAME);
        String verifiedPw = (String) sessionMethods.getSessionAttribute(request, StringConst.VERIFIED_PW);

        if (verifiedEmail == null || !verifiedEmail.equals(userRegisterDto.getEmail())) {
            bindingResult.rejectValue("email", "email.address.notVerified");
        }

        if (verifiedName == null || !verifiedName.equals(userRegisterDto.getName())) {
            bindingResult.rejectValue("name", "name.name.notVerified");
        }

        if (verifiedPw == null || !verifiedPw.equals(userRegisterDto.getPw())) {
            bindingResult.rejectValue("pwConfirmation", "pw.pwConfirmation.notVerified");
        }

        if (bindingResult.hasErrors()) {
            return "pages/register";
        }


        try {
            userService.register(userRegisterDto);
        } catch (RuntimeException e) {
            throw new SQLException();
        }

        fileService.saveProfileImage(userRegisterDto.getUserId(), multipartFile);

        sessionMethods.invalidateSession(request);

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginFormDto loginForm, Model model) {
        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

        return "pages/login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @Validated @ModelAttribute LoginFormDto loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/login";
        }

        LoginMemberDto loginMember = userService.login(loginForm);

        if (loginMember == null) {
            bindingResult.reject(StringConst.LOGIN_FAILURE);
        }

        sessionMethods.invalidateSession(request);
        sessionMethods.login(request, loginMember);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        sessionMethods.invalidateSession(request);

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/home";
    }
}

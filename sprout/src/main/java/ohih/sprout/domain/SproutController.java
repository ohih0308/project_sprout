package ohih.sprout.domain;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.post.service.PostService;
import ohih.sprout.session.SessionMethods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SproutController {

    private final PostService postService;
    private final FileService fileService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(StringConst.CURRENT_POST_LIST, postService.getCurrentPost());

        model.addAttribute(StringConst.EVENT_FILE_LIST, fileService.getEventFileList());

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/home";
    }

    @GetMapping("/chat")
    public String chat(Model model) {

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/chat";
    }

    @GetMapping("/support")
    public String support(Model model) {
        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);
        return "pages/support";
    }
}

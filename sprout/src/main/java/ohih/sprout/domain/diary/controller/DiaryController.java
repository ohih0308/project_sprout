package ohih.sprout.domain.diary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.Paging;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Utilities;
import ohih.sprout.domain.diary.service.DiaryService;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import ohih.sprout.domain.user.dto.LoginMemberDto;
import ohih.sprout.domain.user.service.UserService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/diary/{name}")
@RequiredArgsConstructor
@Slf4j
public class DiaryController {


    private final DiaryService diaryService;
    private final UserService userService;
    private final FileService fileService;


    @GetMapping("")
    public String diary(@Nullable @SessionAttribute LoginMemberDto loginMember,
                        @PathVariable String name, Model model, Paging paging) {
        Long userId = userService.getUserIdByName(name);
        Long totalDiaryCount = diaryService.getTotalDiaryCountByName(name);
        paging = Utilities.getPaging(totalDiaryCount, paging, IntegerConst.DIARY_LIST_SIZE);

        model.addAttribute(StringConst.OWNER, name);
        model.addAttribute(StringConst.OWNER_ID, userId);

        model.addAttribute(StringConst.TOTAL_DIARY_COUNT, totalDiaryCount);
        model.addAttribute(StringConst.TODAY_DIARY_COUNT, diaryService.getTodayDiaryCountByName(name));


        model.addAttribute(StringConst.DIARY_LIST, diaryService.filterDiaryList(name, loginMember, diaryService.getDiaryListByName(name, paging)));
        model.addAttribute(StringConst.PAGING, paging);

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

        return "pages/diary";
    }
}

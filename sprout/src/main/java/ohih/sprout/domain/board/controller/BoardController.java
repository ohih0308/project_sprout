package ohih.sprout.domain.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.IntegerConst;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.Paging;
import ohih.sprout.domain.Search;
import ohih.sprout.domain.board.service.BoardService;
import ohih.sprout.domain.category.service.CategoryService;
import ohih.sprout.domain.file.dto.AdFileDto;
import ohih.sprout.domain.file.fileService.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static ohih.sprout.domain.Utilities.getPaging;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;
    private final FileService fileService;

    @GetMapping("/list")
    public String boardList(Model model) {
        model.addAttribute(StringConst.BOARD_LIST, boardService.getBoardList());
        model.addAttribute(StringConst.CATEGORY_LIST, categoryService.getCategoryList());

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

        return "pages/board-list";
    }

    @GetMapping("/{boardName}")
    public String board(Model model,
                        @PathVariable String boardName, Paging paging, Search search) {
        Long totalCount = boardService.getTotalCountByBoard(boardName, search);
        paging = getPaging(totalCount, paging, IntegerConst.POST_LIST_SIZE);


        model.addAttribute(StringConst.BOARD_NAME, boardName);
        model.addAttribute(StringConst.SEARCH, search);
        model.addAttribute(StringConst.PAGING, paging);

        model.addAttribute(StringConst.POST_LIST, boardService.getPostListByBoard(boardName, paging, search));

        List<AdFileDto> adFileList = fileService.getAdFileList();
        model.addAttribute(StringConst.AD_FILE_LIST, adFileList);

        return "pages/board";
    }
}

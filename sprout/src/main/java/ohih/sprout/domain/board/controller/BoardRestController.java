package ohih.sprout.domain.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.board.dto.BoardDto;
import ohih.sprout.domain.board.service.BoardService;
import ohih.sprout.domain.category.dto.CategoryDto;
import ohih.sprout.domain.category.service.CategoryService;
import ohih.sprout.exception.customException.ValueDuplicatedException;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BoardRestController {
    private final CategoryService categoryService;
    private final BoardService boardService;

    private final MessageSource messageSource;

    @PostMapping("/admin/create-board")
    public SimpleResponse createBoard(HttpServletRequest request,
                                      @Validated BoardDto boardDto, BindingResult bindingResult) throws SQLException, BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        if (categoryService.checkCategoryDuplicated(boardDto.getBoardName())) {
            throw new ValueDuplicatedException(StringConst.BOARD, StringConst.BOARD_NAME, boardDto.getBoardName());
        }

        SimpleResponse simpleResponse = new SimpleResponse();

        Boolean categoryExistence = false;

        List<CategoryDto> categoryList = categoryService.getCategoryList();

        for (CategoryDto categoryDto : categoryList) {
            if (categoryService.checkCategoryDuplicated(categoryDto.getCategoryName())) {
                categoryExistence = true;
            }
        }

        if (categoryExistence == false) {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage("categoryNotExist",
                    new String[]{boardDto.getCategoryName()},
                    request.getLocale()));
        } else {
            boardService.createBoard(boardDto);
            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage("createBoard",
                    new String[]{boardDto.getCategoryName()},
                    request.getLocale()));
        }

        return simpleResponse;
    }

    @PostMapping("/admin/delete-board")
    public SimpleResponse deleteBoard(HttpServletRequest request, BoardDto boardDto) throws SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        if (!boardService.checkBoardDuplicated(boardDto.getBoardName())) {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage("deleteBoardFailed",
                    new String[]{boardDto.getBoardName()},
                    request.getLocale()));

        } else {
            boardService.deleteBoard(boardDto.getBoardName());
            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage("deleteBoard",
                    new String[]{boardDto.getBoardName()},
                    request.getLocale()));
        }

        return simpleResponse;
    }
}

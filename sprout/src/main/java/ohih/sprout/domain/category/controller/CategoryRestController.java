package ohih.sprout.domain.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.board.dto.BoardDto;
import ohih.sprout.domain.board.service.BoardService;
import ohih.sprout.domain.category.dto.CategoryDto;
import ohih.sprout.domain.category.service.CategoryService;
import ohih.sprout.domain.post.service.PostService;
import ohih.sprout.exception.customException.ValueDuplicatedException;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;
    private final MessageSource messageSource;


    @PostMapping("/admin/create-category")
    public SimpleResponse createCategory(HttpServletRequest request,
                                         @Validated CategoryDto categoryDto,
                                         BindingResult bindingResult) throws BindException, SQLException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        if (categoryService.checkCategoryDuplicated(categoryDto.getCategoryName())) {
            throw new ValueDuplicatedException(StringConst.CATEGORY, StringConst.CATEGORY_NAME, categoryDto.getCategoryName());
        }

        categoryService.createCategory(categoryDto.getCategoryName());

        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setResult(true);
        simpleResponse.setMessage(messageSource.getMessage("createCategory",
                new String[]{categoryDto.getCategoryName()},
                request.getLocale()));

        return simpleResponse;
    }

    @PostMapping("/admin/delete-category")
    public SimpleResponse deleteCategory(HttpServletRequest request,
                                         CategoryDto categoryDto) throws SQLException {
        SimpleResponse simpleResponse = new SimpleResponse();

        if (!categoryService.checkCategoryDuplicated(categoryDto.getCategoryName())) {
            simpleResponse.setResult(false);
            simpleResponse.setMessage(messageSource.getMessage("deleteCategoryFailed",
                    new String[]{categoryDto.getCategoryName()},
                    request.getLocale()));
        } else {
            categoryService.deleteCategory(categoryDto.getCategoryName());
            simpleResponse.setResult(true);
            simpleResponse.setMessage(messageSource.getMessage("deleteCategory",
                    new String[]{categoryDto.getCategoryName()},
                    request.getLocale()));
        }

        return simpleResponse;

    }
}

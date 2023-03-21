package ohih.sprout.exception;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.StringConst;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

//@ControllerAdvice(assignableTypes = {PostController.class, PostRestController.class})
@RequiredArgsConstructor
public class PostExceptionHandler {

    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException.class)
    public String iOException(HttpServletRequest request, Model model) {
        String boardName = request.getParameter(StringConst.BOARD_NAME);

        model.addAttribute(StringConst.MESSAGE, messageSource.getMessage(
                "IOException",
                null,
                request.getLocale()));

        return "redirect:/board/" + boardName;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public String sQLException(HttpServletRequest request, Model model) {
        String boardName = request.getParameter(StringConst.BOARD_NAME);

        model.addAttribute(StringConst.MESSAGE, messageSource.getMessage(
                "SQLException",
                null,
                request.getLocale()));

        model.addAttribute(StringConst.BOARD_NAME, boardName);

        return "pages/home";
    }
}

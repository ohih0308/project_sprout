package ohih.sprout.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.StringConst;
import ohih.sprout.domain.diary.controller.DiaryRestController;
import ohih.sprout.domain.user.controller.UserController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice(assignableTypes = {UserController.class, DiaryRestController.class})
@RequiredArgsConstructor
public class UserExceptionHandler {

    private final MessageSource messageSource;


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException.class)
    public String iOException(HttpServletRequest request, Model model) {
        model.addAttribute(StringConst.MESSAGE, messageSource.getMessage(
                "IOException",
                null,
                request.getLocale()));

        return "pages/home";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLException.class)
    public String sQLException(HttpServletRequest request, Model model) {
        model.addAttribute(StringConst.MESSAGE, messageSource.getMessage(
                "SQLException",
                null,
                request.getLocale()));

        return "pages/home";
    }
}

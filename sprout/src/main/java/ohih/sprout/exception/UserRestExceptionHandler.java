package ohih.sprout.exception;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.SimpleResponse;
import ohih.sprout.domain.comment.controller.CommentRestController;
import ohih.sprout.domain.mail.controller.MailRestController;
import ohih.sprout.domain.user.controller.UserRestController;
import ohih.sprout.exception.customException.ValueDuplicatedException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestControllerAdvice(assignableTypes = {
        UserRestController.class,
        MailRestController.class,
        CommentRestController.class})
@RequiredArgsConstructor
public class UserRestExceptionHandler {

    private final MessageSource messageSource;


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public SimpleResponse bindException(HttpServletRequest request, BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();

        String field = null;
        String code = null;

        if (fieldError != null) {
            field = fieldError.getField();
            code = fieldError.getCode();
        }

        SimpleResponse simpleResponse = new SimpleResponse();

        simpleResponse.setResult(false);
        simpleResponse.setMessage(
                messageSource.getMessage(
                        "BindException",
                        new String[]{field, code},
                        request.getLocale()));

        return simpleResponse;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValueDuplicatedException.class)
    public SimpleResponse duplicatedException(HttpServletRequest request, ValueDuplicatedException e) {
        SimpleResponse simpleResponse = new SimpleResponse();

        simpleResponse.setResult(false);
        simpleResponse.setMessage(
                messageSource.getMessage("ValueDuplicatedException",
                        new String[]{e.getValue()},
                        request.getLocale()));

        return simpleResponse;
    }

}

package ohih.sprout.exception.customException;

import lombok.Getter;

@Getter
public class ValueDuplicatedException extends RuntimeException {

    private String object;
    private String filed;
    private String value;
    private String message;


    public ValueDuplicatedException(String object, String filed, String value) {
        this.object = object;
        this.filed = filed;
        this.value = value;
    }
}

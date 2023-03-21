package ohih.sprout.domain.category.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CategoryDto {
    @Pattern(regexp = "^[a-zA-Z0-9+]{4,30}$")
    private String categoryName;
}

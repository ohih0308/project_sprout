package ohih.sprout.domain.category.mapper;

import ohih.sprout.domain.category.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDto> getCategoryList();

    int checkCategoryDuplicated(String categoryName);

    int createCategory(String categoryName);

    int deleteCategory(String categoryName);
}

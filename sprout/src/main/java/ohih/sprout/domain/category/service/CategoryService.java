package ohih.sprout.domain.category.service;

import lombok.RequiredArgsConstructor;
import ohih.sprout.domain.category.dto.CategoryDto;
import ohih.sprout.domain.category.mapper.CategoryMapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;


    public List<CategoryDto> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public Boolean checkCategoryDuplicated(String categoryName) {
        if (categoryMapper.checkCategoryDuplicated(categoryName) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void createCategory(String categoryName) throws SQLException {
        if (categoryMapper.createCategory(categoryName) != 1) {
            throw new SQLException();
        }
    }

    public void deleteCategory(String categoryName) throws SQLException {
        if (categoryMapper.deleteCategory(categoryName) != 1) {
            throw new SQLException();
        }
    }

}

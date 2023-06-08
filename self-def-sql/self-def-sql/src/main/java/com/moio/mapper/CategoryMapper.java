package com.moio.mapper;

import com.moio.entity.BasicSql;
import com.moio.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description category mapper
 *
 * @author molinchang
 */
@Repository
public interface CategoryMapper {

    /**
     * query list by page.
     *
     * @param map index-size map
     * @return category list
     */
    List<Category> pages(Map<String, Object> map);

    /**
     * query list.
     *
     * @return category list
     */
    List<Category> list();

    /**
     * find category by id.
     *
     * @param indexC index_c
     * @return category
     */
    Category getCategoryByIndex(Integer indexC);

    /**
     * add category.
     *
     * @param category category
     * @return 1 --- success , 0 --- failed
     */
    Integer addCategory(Category category);

    /**
     * update category.
     *
     * @param category category
     * @return 1 --- success, 0 --- failed
     */
    Integer updateCategory(Category category);

    /**
     * delete category.
     *
     * @param id category id
     * @return 1 --- success, 0 --- falied
     */
    Integer deleteCategory(Integer id);

    /**
     * get category by content.
     *
     * @param content content
     * @return category
     */
    Category getCategoryByContent(String content);
}

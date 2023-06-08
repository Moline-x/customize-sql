package com.moio.service;

import com.moio.common.exception.ContentExistedException;
import com.moio.entity.Category;

import java.util.List;

/**
 * @description category service interface
 *
 * @author molinchang
 */
public interface CategoryService {

    /**
     * query all category.
     *
     * @return category list.
     */
    List<Category> list();

    /**
     * query category by id.
     *
     * @return category.
     */
    Category getByIndex(Integer index);

    /**
     * update category.
     *
     * @param category category
     * @return true -- success, false -- failed
     */
    Boolean update(Category category);

    /**
     * add category
     *
     * @param category category
     * @return true -- success, false -- failed
     */
    Boolean add(Category category) throws ContentExistedException;

    /**
     * delete category by id
     *
     * @param id id
     * @return true -- success, false -- failed
     */
    Boolean deleteById(Integer id);

    /**
     * query category list by page
     *
     * @param index  index
     * @param size   page size
     * @param search search string
     * @return category list
     */
    List<Category> pages(Integer index, Integer size, String search);
}

package com.moio.service.impl;

import com.moio.common.exception.ContentExistedException;
import com.moio.entity.Category;
import com.moio.mapper.CategoryMapper;
import com.moio.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description category service , build up logic.
 *
 * @author molinchang
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * query all category.
     *
     * @return category list.
     */
    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    /**
     * query category by id.
     *
     * @param index index_c
     * @return category.
     */
    @Override
    public Category getByIndex(Integer index) {
        return categoryMapper.getCategoryByIndex(index);
    }

    /**
     * update category.
     *
     * @param category category
     * @return true -- success, false -- failed
     */
    @Override
    public Boolean update(Category category) {
        return categoryMapper.updateCategory(category) == 1;
    }

    /**
     * add category
     *
     * @param category category
     * @return true -- success, false -- failed
     */
    @Override
    public Boolean add(Category category) throws ContentExistedException {

        // search content
        Category categoryByContent = categoryMapper.getCategoryByContent(category.getContent());
        if (categoryByContent != null) {
            throw new ContentExistedException("case已存在, 编号:" + categoryByContent.getIndexC() + "");
        } else {
            return categoryMapper.addCategory(category) == 1;
        }
    }

    /**
     * delete category by id
     *
     * @param id id
     * @return true -- success, false -- failed
     */
    @Override
    public Boolean deleteById(Integer id) {
        return categoryMapper.deleteCategory(id) == 1;
    }

    /**
     * query category list by page
     *
     * @param index  index
     * @param size   page size
     * @param search search string
     * @return category list
     */
    @Override
    public List<Category> pages(Integer index, Integer size, String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("index", (index - 1) * size);
        map.put("size", size);
        map.put("search", search);
        return categoryMapper.pages(map);
    }
}

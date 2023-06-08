package com.moio.service.impl;

import com.moio.entity.CategorySql;
import com.moio.mapper.CategorySqlMapper;
import com.moio.service.CategorySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description category sql service.
 *
 * @author molinchang
 */
@Service
public class CategorySqlServiceImpl implements CategorySqlService {

    @Autowired
    CategorySqlMapper categorySqlMapper;

    /**
     * query all category sql list.
     *
     * @return category sql list
     */
    @Override
    public List<CategorySql> list() {
        return categorySqlMapper.list();
    }

    /**
     * add one Category sql relation to table.
     *
     * @param cid category id
     * @param sid basic sql id
     * @param seq sequence order
     * @return true --- success , false --- failed.
     */
    @Override
    public Boolean add(Integer cid, Integer sid, Integer seq) {

        return categorySqlMapper.addCategorySql(new CategorySql(cid, sid, seq)) == 1;
    }

    /**
     * delete one category with multiple sql relation to table.
     *
     * @param cid     category id
     * @param sidList sid list
     * @return true --- success, false --- failed.
     */
    @Override
    public Boolean delete(Integer cid, List<Integer> sidList) {
        return categorySqlMapper.deleteCategorySql(cid, sidList) >= 1;
    }

    /**
     * get max sequence number for bind sql.
     *
     * @param cid     category id
     * @param sidList sid list
     * @return max sequence number
     */
    @Override
    public Integer getMaxSeq(Integer cid, List<Integer> sidList) {
        return categorySqlMapper.getMaxSeq(cid, sidList);
    }

    /**
     * get single sequence number by cid and sid.
     *
     * @param cid category id
     * @param sid basic sql id
     * @return sequence number
     */
    @Override
    public Integer getSeq(Integer cid, Integer sid) {
        return categorySqlMapper.getSeq(cid, sid);
    }

    /**
     * update category sql with new position.
     *
     * @param categorySql category sql
     * @return true ---- success, false ---- failed
     */
    @Override
    public Boolean update(CategorySql categorySql) {
        return categorySqlMapper.updateCategorySql(categorySql) == 1;
    }
}

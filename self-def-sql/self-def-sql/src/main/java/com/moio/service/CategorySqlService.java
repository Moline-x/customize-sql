package com.moio.service;

import com.moio.entity.CategorySql;

import java.util.List;

public interface CategorySqlService {

    /**
     * query all category sql list.
     *
     * @return category sql list
     */
    List<CategorySql> list();

    /**
     * add one Category sql relation to table.
     *
     * @param cid category id
     * @param sid basic sql id
     * @param seq sequence order
     * @return true --- success , false --- failed.
     */
    Boolean add(Integer cid, Integer sid, Integer seq);

    /**
     * delete one category with multiple sql relation to table.
     *
     * @param cid      category id
     * @param sidList  sid list
     * @return true --- success, false --- failed.
     */
    Boolean delete(Integer cid, List<Integer> sidList);

    /**
     * get max sequence number for bind sql.
     *
     * @param cid       category id
     * @param sidList   sid list
     * @return max sequence number
     */
    Integer getMaxSeq(Integer cid, List<Integer> sidList);

    /**
     * get single sequence number by cid and sid.
     *
     * @param cid   category id
     * @param sid   basic sql id
     * @return  sequence number
     */
    Integer getSeq(Integer cid, Integer sid);

    /**
     * update category sql with new position.
     *
     * @param categorySql category sql
     * @return true ---- success, false ---- failed
     */
    Boolean update(CategorySql categorySql);
}

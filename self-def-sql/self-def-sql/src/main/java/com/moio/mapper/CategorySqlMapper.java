package com.moio.mapper;

import com.moio.entity.CategorySql;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description category sql mapper
 *
 * @author molinchang
 */
@Repository
public interface CategorySqlMapper {

    /**
     * query all category sql
     *
     * @return category sql list
     */
    List<CategorySql> list();

    /**
     * add category sql.
     *
     * @param categorySql category sql
     * @return 1 --- success, 0 --- failed
     */
    Integer addCategorySql(CategorySql categorySql);

    /**
     * delete category sql.
     *
     * @param cid category id
     * @param sidList  sid list
     * @return 1 --- success, 0 --- failed
     */
    Integer deleteCategorySql(@Param("cid") Integer cid, @Param("sidList") List<Integer> sidList);

    /**
     * get max sequence number for bind sql.
     *
     * @param cid       category id
     * @param sidList   sid list
     * @return max sequence number.
     */
    Integer getMaxSeq(@Param("cid") Integer cid, @Param("sidList") List<Integer> sidList);

    /**
     * get single sequence number by cid and sid.
     *
     * @param cid   category id
     * @param sid   basic sql id
     * @return sequence number
     */
    Integer getSeq(Integer cid, Integer sid);

    /**
     * update category sql.
     *
     * @param categorySql category sql
     * @return 1 --- success, 0 --- failed
     */
    Integer updateCategorySql(CategorySql categorySql);
}

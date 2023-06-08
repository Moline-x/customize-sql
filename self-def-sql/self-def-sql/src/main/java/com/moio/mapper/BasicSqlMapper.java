package com.moio.mapper;

import com.moio.entity.BasicSql;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description basic sql mapper interface
 *
 * @author molinchang
 */
@Repository
public interface BasicSqlMapper {

    /**
     * query list by page.
     *
     * @param map index-size map
     * @return basicSql list
     */
    List<BasicSql> pages(Map<String, Object> map);

    /**
     * query list.
     *
     * @return basicSql list
     */
    List<BasicSql> list();

    /**
     * query basic sql list by category id.
     *
     * @param indexC category index code
     * @return basic sql list
     */
    BasicSql getBasicSqlByCategoryIndex(Integer indexC);

    /**
     * find basicSql by id.
     *
     * @param id basicSql id
     * @return basicSql
     */
    BasicSql getBasicSqlById(Integer id);

    /**
     * add basicSql.
     *
     * @param basicSql basicSql
     * @return 1 --- success , 0 --- failed
     */
    Integer addBasicSql(BasicSql basicSql);

    /**
     * update basicSql.
     *
     * @param basicSql basicSql
     * @return 1 --- success, 0 --- failed
     */
    Integer updateBasicSql(BasicSql basicSql);

    /**
     * delete basicSql.
     *
     * @param id id
     * @return 1 --- success, 0 --- falied
     */
    Integer deleteBasicSql(Integer id);

    /**
     * get basic sql by sql value and sql key.
     *
     * @param basicSql basic sql
     * @return basic sql
     */
    BasicSql getBasicSqlByValueAndKey(BasicSql basicSql);
}

package com.moio.service;

import com.moio.common.exception.SqlExistedException;
import com.moio.entity.BasicSql;

import java.util.List;

/**
 * @description basic sql service interface.
 *
 * @author molinchang
 */
public interface BasicSqlService {

    /**
     * query all basicSql.
     *
     * @return basicSql list.
     */
    List<BasicSql> list();

    /**
     * query basicSql by id.
     *
     * @return basicSql.
     */
    BasicSql getById(Integer id);

    /**
     * update basicSql.
     *
     * @param basicSql basic sql
     * @return true -- success, false -- failed
     */
    Boolean update(BasicSql basicSql) throws SqlExistedException;

    /**
     * add basicSql
     *
     * @param basicSql basicSql
     * @return true -- success, false -- failed
     */
    Boolean add(BasicSql basicSql) throws SqlExistedException;

    /**
     * delete basicSql by id
     *
     * @param id id
     * @return true -- success, false -- failed
     */
    Boolean deleteById(Integer id);

    /**
     * query basicSql list by page
     *
     * @param index  index
     * @param size   page size
     * @param search search string
     * @return basic sql list
     */
    List<BasicSql> pages(Integer index, Integer size, String search);
}

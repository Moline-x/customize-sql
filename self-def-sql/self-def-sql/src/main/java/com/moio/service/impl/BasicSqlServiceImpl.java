package com.moio.service.impl;

import com.moio.common.exception.SqlExistedException;
import com.moio.entity.BasicSql;
import com.moio.mapper.BasicSqlMapper;
import com.moio.service.BasicSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description basic sql service
 *
 * @author molinchang
 */
@Service
public class BasicSqlServiceImpl implements BasicSqlService {

    @Autowired
    BasicSqlMapper basicSqlMapper;

    /**
     * query all basicSql.
     *
     * @return basicSql list.
     */
    @Override
    public List<BasicSql> list() {
        List<BasicSql> list = new ArrayList<>();
        List<BasicSql> sqlList = basicSqlMapper.list();
        if (sqlList != null) {
            return sqlList;
        }
        return list;
    }

    /**
     * query basicSql by id.
     *
     * @param id id
     * @return basicSql.
     */
    @Override
    public BasicSql getById(Integer id) {
        return basicSqlMapper.getBasicSqlById(id);
    }

    /**
     * update basicSql.
     *
     * @param basicSql basicSql
     * @return true -- success, false -- failed
     */
    @Override
    public Boolean update(BasicSql basicSql) throws SqlExistedException {

        boolean result;
        // search sql
        BasicSql basicSqlByValueAndKey = basicSqlMapper.getBasicSqlByValueAndKey(basicSql);
        if (basicSqlByValueAndKey != null) {
            if (basicSqlByValueAndKey.getId().equals(basicSql.getId())) {
                result = basicSqlMapper.updateBasicSql(basicSql) == 1;
            } else {
                throw new SqlExistedException("已存在该sql语句，编号:" + basicSqlByValueAndKey.getId() + "");
            }
        } else {
            result = basicSqlMapper.updateBasicSql(basicSql) == 1;
        }

        return result;
    }

    /**
     * add basicSql
     *
     * @param basicSql basicSql
     * @return true -- success, false -- failed
     */
    @Override
    public Boolean add(BasicSql basicSql) throws SqlExistedException {

        boolean result;
        // search sql
        BasicSql basicSqlByValue = basicSqlMapper.getBasicSqlByValueAndKey(basicSql);
        if (basicSqlByValue != null) {
            throw new SqlExistedException("已存在该sql语句，编号:" + basicSqlByValue.getId() + "");
        } else {
            result = basicSqlMapper.addBasicSql(basicSql) == 1;
        }
        return result;
    }

    /**
     * delete basicSql by id
     *
     * @param id id
     * @return true -- success, false -- failed
     */
    @Override
    public Boolean deleteById(Integer id) {
        return basicSqlMapper.deleteBasicSql(id) == 1;
    }

    /**
     * query basicSql list by page
     *
     * @param index  index
     * @param size   page size
     * @param search search string
     * @return basic sql list
     */
    @Override
    public List<BasicSql> pages(Integer index, Integer size, String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("index", (index - 1) * size);
        map.put("size", size);
        map.put("search", search);
        return basicSqlMapper.pages(map);
    }
}

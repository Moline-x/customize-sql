package com.moio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description Basic sql entity , stored basic sql.
 *
 * @author molinchang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasicSql {

    /**
     * id.
     */
    private Integer id;
    /**
     * sql key.
     */
    private String sqlKey;
    /**
     * sql value.
     */
    private String sqlValue;

    /**
     * category list.
     */
    private List<Category> categoryList;

    public BasicSql(Integer id, String sqlKey, String sqlValue) {
        this.id = id;
        this.sqlKey = sqlKey;
        this.sqlValue = sqlValue;
    }

    public BasicSql(String sqlKey, String sqlValue) {
        this.sqlKey = sqlKey;
        this.sqlValue = sqlValue;
    }
}

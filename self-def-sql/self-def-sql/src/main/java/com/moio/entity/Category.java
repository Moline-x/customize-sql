package com.moio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description category entity
 *
 * @author molinchang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    /**
     * id.
     */
    private Integer id;
    /**
     * category content.
     */
    private String content;
    /**
     * category index.
     */
    private Integer indexC;

    /**
     * basic sql list
     */
    private List<BasicSql> basicSqlList;
}

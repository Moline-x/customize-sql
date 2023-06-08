package com.moio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description category sql entity, stored relation with category and basic sql.
 *
 * @author molinchang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategorySql {

    /**
     * category id, one of union primary key.
     */
    private Integer cid;

    /**
     * basic sql id, one of union primary key.
     */
    private Integer sid;

    /**
     * sequence order.
     */
    private Integer seq;
}

package com.moio.common.enums;

import lombok.Getter;

/**
 * @author molinchang
 *
 * @description menu view enum
 */
@Getter
public enum ViewEnum {

    ALL_CATEGORY_BELOW("全部分类条目如下: "),
    KEY_SYMBOL_LEFT("   key:【"),
    ALL_SQL_BELOW("全部SQL语句如下: "),
    ;


    private final String content;

    ViewEnum(String content) {
        this.content = content;
    }
}

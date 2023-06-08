package com.moio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author molinchang
 *
 * @description bind sql dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BindSqlDto {

    private String categoryIndex;

    private String content;

    private String searchText;

    private String searchKey;

}

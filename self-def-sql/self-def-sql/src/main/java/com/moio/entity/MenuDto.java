package com.moio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author molinchang
 *
 * @description menu dto
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuDto {

    private List<String> keyList;

    private Category category;
}

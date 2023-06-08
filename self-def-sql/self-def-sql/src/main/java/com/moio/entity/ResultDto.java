package com.moio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author molinchang
 *
 * @description this is a DTO for manager layout return result
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultDto<T> {

    private String message;

    private boolean updateResult;

    private T data;
}

package com.wilder.power_supply.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Description：处理导入excel出现的异常
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelException extends Exception {
    /**
     * 异常编号
     */
    private int exceptionCode;
    /**
     * 异常信息描述
     */
    private String message;

}

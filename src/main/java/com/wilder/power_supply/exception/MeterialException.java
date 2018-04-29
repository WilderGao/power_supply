package com.wilder.power_supply.exception;

import lombok.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/20
 * @Discription：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterialException extends Exception {
    /**
     * 异常编号
     */
    private int exceptionCode;
    /**
     * 异常信息描述
     */
    private String message;

}

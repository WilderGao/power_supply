package com.wilder.power_supply.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilder Gao
 * time:2018/5/1
 * Description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceException extends Exception{
    /**
     * 异常编号
     */
    private int exceptionCode;
    /**
     * 异常信息描述
     */
    private String message;
}

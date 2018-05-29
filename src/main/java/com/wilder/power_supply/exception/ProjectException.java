package com.wilder.power_supply.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Discription：工程引发的异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectException extends Exception {
    /**
     * 状态码
     * @param message
     */
    private int state;

    /**
     * 状态信息
     */
    private String message;

}

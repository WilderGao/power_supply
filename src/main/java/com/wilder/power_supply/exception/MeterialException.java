package com.wilder.power_supply.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author:Wilder Gao
 * @time:2018/4/20
 * @Discription：
 */
public class MeterialException extends Exception {
    /**
     * 异常编号
     */
    @Getter @Setter
    private int exceptionCode;
    /**
     * 异常信息描述
     */
    @Getter @Setter
    private String message;

    public MeterialException(){
        super();
    }

    public MeterialException(int exceptionCode, String message){
        super();
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

}

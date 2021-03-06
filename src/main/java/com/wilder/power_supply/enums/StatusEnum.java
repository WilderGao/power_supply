package com.wilder.power_supply.enums;

/**
 * @author Wilder Gao
 * time:2018/4/20
 * Description：
 */
public enum StatusEnum {
    OK(200),        //正常
    PATAMETER_ERROR(210),   //传入参数异常

    ERROR(500);

    private int state;
    StatusEnum(int state){
        this.state = state;
    }

    public int getState(){
        return this.state;
    }
}

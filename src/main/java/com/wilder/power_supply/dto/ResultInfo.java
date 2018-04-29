package com.wilder.power_supply.dto;

import lombok.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/20
 * @Discription：返回格式类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo<T> {
    /**
     * 状态信息
     */
    private int status;

    /**
     * 状态信息描述
     */
    private String message;

    /**
     * 状态信息对应的内容
     */
    private T info;

}

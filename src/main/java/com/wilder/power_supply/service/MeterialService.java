package com.wilder.power_supply.service;

import com.wilder.power_supply.model.Meterial;

import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：
 */
public interface MeterialService {

    /**
     * 将excel中的内容导入到数据库表中
     * @param excelPath
     * @param
     */
    void meterialHandler(String excelPath) throws Exception;
}

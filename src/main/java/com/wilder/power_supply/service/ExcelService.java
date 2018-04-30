package com.wilder.power_supply.service;

import com.wilder.power_supply.exception.ExcelException;

/**
 * @author:Wilder Gao
 * @time:2018/4/30
 * @Discription：
 */
public interface ExcelService {

    /**
     * 将材料表 excel 中的内容导入到数据库表中
     * @param excelPath
     * @param
     */
    void excelHandler(String excelPath, String className) throws ExcelException;


}

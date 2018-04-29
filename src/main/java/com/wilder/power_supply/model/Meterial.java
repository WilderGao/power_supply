package com.wilder.power_supply.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：材料实体类
 */
@Data
public class Meterial extends BaseRowModel{
    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private int meterialId;

    /**
     * 物资编码
     */
    @ExcelProperty(value = "物资编码")
    private String meterialCode;

    /**
     * 物资名称
     */
    @ExcelProperty(value = "物资名称")
    private String meterialName;

    /**
     * 规格型号
     */
    @ExcelProperty(value = "规格型号")
    private String meterialModel;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位")
    private String meterialUnit;

    /**
     * 单价（隐藏列）
     */
    @ExcelProperty(value = "单价（隐藏列）")
    private String meterialPrice;

    /**
     * 是否业扩储备物资
     */
    @ExcelProperty(value = "是否业扩储备物资")
    private String meterialCheck;

    /**
     * 备注1（隐藏列）
     */
    @ExcelProperty(value = "备注1（隐藏列）")
    private String meterialAttention;
}

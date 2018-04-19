package com.wilder.power_supply.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：材料实体类
 */
public class Meterial extends BaseRowModel{
    @Getter @Setter
    @ExcelProperty(value = "序号")
    private int meterialId;

    @ExcelProperty(value = "物资编码")
    @Getter @Setter
    private String meterialCode;

    @ExcelProperty(value = "物资名称")
    @Getter @Setter
    private String meterialName;

    @ExcelProperty(value = "规格型号")
    @Getter @Setter
    private String meterialModel;

    @ExcelProperty(value = "单位")
    @Getter @Setter
    private String meterialUnit;

    @ExcelProperty(value = "单价（隐藏列）")
    @Getter @Setter
    private String meterialPrice;

    @ExcelProperty(value = "是否业扩储备物资")
    @Getter @Setter
    private String meterialCheck;

    @ExcelProperty(value = "备注1（隐藏列）")
    @Getter @Setter
    private String meterialAttention;

    @Override
    public String toString() {
        return "Meterial{" +
                "meterialId=" + meterialId +
                ", meterialCode='" + meterialCode + '\'' +
                ", meterialName='" + meterialName + '\'' +
                ", meterialModel='" + meterialModel + '\'' +
                ", meterialUnit='" + meterialUnit + '\'' +
                ", meterialPrice=" + meterialPrice +
                ", meterialCheck='" + meterialCheck + '\'' +
                ", meterialAttention='" + meterialAttention + '\'' +
                '}';
    }
}

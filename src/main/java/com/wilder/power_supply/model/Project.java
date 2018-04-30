package com.wilder.power_supply.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import javax.xml.ws.WebServiceRef;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：工程类名
 */
@Data
public class Project extends BaseRowModel {
    /**
     * 工程Id
     */
    @ExcelProperty(value = "序号")
    private int projectId;

    /**
     * 工程所在区局
     */
    @ExcelProperty(value = "区局")
    private String district;

    /**
     * 批次
     */
    @ExcelProperty(value = "批次")
    private String batch;

    /**
     * 项目负责的供电所
     */
    @ExcelProperty(value = "供电所")
    private String powerSupply;

    /**
     * 工程编号
     */
    @ExcelProperty(value = "项目编号")
    private String projectCode;

    /**
     * 工程名称
     */
    @ExcelProperty(value = "工程名称")
    private String projectName;

    /**
     * 材料列表
     */
    private List<Meterial> meterials;
}

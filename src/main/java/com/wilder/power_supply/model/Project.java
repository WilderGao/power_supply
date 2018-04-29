package com.wilder.power_supply.model;

import lombok.Data;

import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：工程类名
 */
@Data
public class Project {
    /**
     * 工程Id
     */
    private int projectId;

    /**
     * 工程名称
     */
    private String projectName;

    /**
     * 工程所在区局
     */
    private String district;

    /**
     * 批次
     */
    private String batch;

    /**
     * 项目负责的供电所
     */
    private String powerSupply;

    /**
     * 工程编号
     */
    private String projectCode;

    /**
     * 材料列表
     */
    private List<Meterial> meterials;
}

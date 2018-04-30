package com.wilder.power_supply.service;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Project;

import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：
 */
public interface ProjectService {

    /**
     * 新建工程逻辑处理
     * @param project
     * @return
     */
    ResultInfo buildProjectHandler(Project project) throws ProjectException;
}

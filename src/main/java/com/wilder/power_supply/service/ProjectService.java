package com.wilder.power_supply.service;

import com.wilder.power_supply.dto.ResultInfo;
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
     * @throws ProjectException
     */
    ResultInfo<String> buildProjectHandler(Project project) throws ProjectException;

    /**
     * 获取工程列表，按照首字母排序
     * @return
     * @throws ProjectException
     */
    ResultInfo<List<Project>> projectListHandler() throws ProjectException;


    /**
     * 获取一个工程需要的详细材料
     * @param projectId
     * @return
     * @throws ProjectException
     */
    ResultInfo<Project> projectDetailHandler(int projectId) throws ProjectException;
}

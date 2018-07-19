package com.wilder.power_supply.service;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Project;

import java.io.IOException;
import java.util.List;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Description：
 */
public interface ProjectService {

    /**
     * 新建工程
     * @param project
     * @param sessionId
     * @param excelPath
     * @return
     * @throws ProjectException
     * @throws IOException
     * @throws ExcelException
     * @throws InterruptedException
     */
    ResultInfo<String> buildProjectHandler(Project project, String sessionId, String excelPath) throws ProjectException, IOException, ExcelException, InterruptedException;

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

    /**
     *
     * 导出历史工程
     * @param projectId 工程Id
     * @param excelPathContent excel目录
     * @return 返回下载路径
     * @throws ProjectException
     * @throws IOException
     * @throws ExcelException
     */
    ResultInfo<String> projectExport(int projectId, String excelPathContent)
            throws ProjectException, IOException, ExcelException, InterruptedException;
}

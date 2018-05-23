package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dao.ProjectDao;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import com.wilder.power_supply.service.ProjectService;
import com.wilder.power_supply.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.wilder.power_supply.enums.StatusStatementEnum.*;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Description：
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public ResultInfo buildProjectHandler(Project project, String sessionId, String excelPath) throws ProjectException, IOException, ExcelException {
        //check if the project complete
        String checkResult = checkProject(project);
        if (!checkResult.equals(PROJECT_COMPLETE)){
            throw new ProjectException(StatusEnum.ERROR.getState(), checkResult);

        }else {
            //check if projectCode exists in database
            int exist = projectDao.projectExist(project.getProjectCode());
            if (exist != 0){
                System.out.println(exist+"是否存在");
                // exist this project , insert fail
                ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.ERROR.getState(), PROJECT_EXIST);
                return resultInfo;

            }else {
                Map<String, List<Meterial>> map = BufferMen.projectMaterialMap;
                List<Meterial> materials = map.get(sessionId);

                if (materials.size() != 0) {
                    project.setMeterials(materials);
                    // insert into project table
                    projectDao.insertNewProject(project);
                    //insert into material-project
                    projectDao.meterialDetail(project.getProjectId(), project.getMeterials());

                    //导出excel表格并返回连接
                    String path = ExcelUtil.exportProject(project, excelPath);
                    ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), OK);
                    resultInfo.setInfo(path);
                    return resultInfo;
                }else {
                    log.error("材料为空");
                    throw new ProjectException(StatusEnum.ERROR.getState(), "材料为空");
                }

            }
        }
    }


    @Override
    public ResultInfo<List<Project>> projectListHandler() throws ProjectException {
        List<Project> projects = projectDao.getProjectList();
        if (projects.size() == 0){
            throw new ProjectException(StatusEnum.ERROR.getState(), " 工程为空 ");
        }else {
            ResultInfo<List<Project>> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "OK", projects);
            return resultInfo;
        }
    }


    @Override
    public ResultInfo<Project> projectDetailHandler(int projectId) throws ProjectException {
        if (projectId <= 0){
            log.error("工程 Id 出错");
            throw new ProjectException(StatusEnum.ERROR.getState(), "工程 Id 出错");
        }else {
            List<Meterial> materials = projectDao.getProjectDetail(projectId);
            if (materials.size() == 0){
                log.error("详细材料为空");
                throw new ProjectException(StatusEnum.ERROR.getState(), "详细材料为空");
            }
            Project project = projectDao.getProjectById(projectId);
            if (project == null) {
                log.error("获取工程失败");
                throw new ProjectException(StatusEnum.ERROR.getState(), "获取工程失败");
            }
            project.setMeterials(materials);
            ResultInfo<Project> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(),
                    "OK", project);
            return resultInfo;
        }
    }


//    @Override
//    public ResultInfo<String> projectExport(int projectId) throws ProjectException {
//        if (projectId <= 0){
//            log.error("工程 Id 出错");
//            throw new ProjectException(StatusEnum.ERROR.getState(), "工程 Id 出错");
//        }else {
//
//        }
//    }

    /**
     * 检验发送的格式是否有问题
     * @param project
     * @return 检验结果
     */
    private String checkProject(Project project){
        if (null == project){
            return PROJECT_EMPTY;
        }else if (null == project.getProjectCode() || null == project.getBatch()
                || null == project.getPowerSupply() || null == project.getDistrict()){
            return PROJECT_NOT_COMPLETE;
        }else {
            return PROJECT_COMPLETE;
        }
    }
}

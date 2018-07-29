package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dao.ProjectDao;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Device;
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
    public ResultInfo<String> buildProjectHandler(Project project, String sessionId, String excelPath) throws ProjectException, IOException, ExcelException, InterruptedException {
        //check if the project complete
        String checkResult = checkProject(project);
        if (!checkResult.equals(PROJECT_COMPLETE)){
            throw new ProjectException(StatusEnum.ERROR.getState(), checkResult);

        }else {
            //check if projectCode exists in database
            int exist = projectDao.projectExist(project.getProjectCode());
            if (exist != 0){
                log.error("==== 该工程已经存在 ====");
                // exist this project , insert fail
                return new ResultInfo<>(StatusEnum.ERROR.getState(), PROJECT_EXIST);

            }else {
                //找到额外添加的材料信息
                Map<String, List<Meterial>> map = BufferMen.projectMaterialMap;
                //找到添加的设备信息
                List<Device> devices = BufferMen.userMap.get(sessionId);
                List<Meterial> materials = map.get(sessionId);

                devices.forEach((v)-> materials.addAll(v.getMeterials()));

                if (null == materials){
                    log.error("==== 材料为空 ====");
                    ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.ERROR.getState(), "没有找到与这个工程有关的材料");
                    resultInfo.setInfo("没有找到与这个工程有关的材料");
                    return resultInfo;
                }

                if (materials.size() != 0) {
                    project.setMeterials(materials);
                    // insert into project table
                    projectDao.insertNewProject(project);
                    //insert into material-project
                    projectDao.meterialDetail(project.getProjectId(), project.getMeterials());

                    //导出excel表格并返回连接
                    String path = ExcelUtil.exportProject(project, excelPath, true);
                    ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), OK);
                    resultInfo.setInfo(path);

                    Thread.sleep(4000);
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
            return new ResultInfo<>(StatusEnum.OK.getState(), "OK", projects);
        }
    }


    @Override
    public ResultInfo<Project> projectDetailHandler(int projectId) throws ProjectException {
        if (projectId <= 0){
            log.error("工程 Id 出错");
            throw new ProjectException(StatusEnum.ERROR.getState(), "工程 Id 出错");
        }else {
            List<Meterial> materials = projectDao.getProjectDetail(projectId);
            log.info("projectId 为："+projectId);
            if (materials.size() == 0){
                log.error("详细材料为空");
                return new ResultInfo<>(StatusEnum.OK.getState(), "详细材料为空");
            }
            Project project = projectDao.getProjectById(projectId);
            if (project == null) {
                log.error("获取工程失败");
                throw new ProjectException(StatusEnum.ERROR.getState(), "获取工程失败");
            }
            project.setMeterials(materials);
            return new ResultInfo<>(StatusEnum.OK.getState(),
                    "OK", project);

        }
    }



    @Override
    public ResultInfo<String> projectExport(int projectId, String excelPathContent)
            throws ProjectException, IOException, ExcelException, InterruptedException {
        if (projectId <= 0){
            log.error("工程 Id 出错");
            throw new ProjectException(StatusEnum.ERROR.getState(), "工程 Id 出错");
        }else {
            Project project = projectDao.getProjectById(projectId);
            project.setMeterials(projectDao.getProjectDetail(projectId));
            if (project.getMeterials() == null || project.getMeterials().size() == 0){
                return new ResultInfo<>(StatusEnum.OK.getState(), "材料为空");

            }
            if (checkProject(project).equals(PROJECT_COMPLETE)){
                String excelName = project.getProjectName()+".xls";
                String excelPath = ExcelUtil.exportProject(project, excelPathContent+excelName, false);
                ResultInfo<String> resultInfo = new ResultInfo<>();
                resultInfo.setStatus(StatusEnum.OK.getState());
                resultInfo.setMessage("导出成功");
                resultInfo.setInfo(excelPath);

                Thread.sleep(3000);
                return resultInfo;
            }else {
                throw new ProjectException(StatusEnum.ERROR.getState(), "导出excel出错");
            }
        }
    }

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

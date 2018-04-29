package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.dao.ProjectDao;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Project;
import com.wilder.power_supply.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wilder.power_supply.enums.StatusStatementEnum.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public ResultInfo buildProjectHandler(Project project) throws ProjectException {
        //check if the project complete
        String checkResult = checkProject(project);
        if (! checkResult.equals(PROJECT_COMPLETE)){
            throw new ProjectException(StatusEnum.ERROR.getState(), checkResult);

        }else {
            //check if projectCode exists in database
            int exist = projectDao.projectExist(project.getProjectCode());
            if (exist != 0){
                // exist this project , insert fail
                ResultInfo resultInfo = new ResultInfo(StatusEnum.ERROR.getState(), PROJECT_EXIST, null);
                return resultInfo;

            }else {
                // insert into project table
                int projectId = projectDao.insertNewProject(project);
                //insert into material-project
                projectDao.meterialDetail(projectId, project.getMeterials());
                ResultInfo resultInfo = new ResultInfo(StatusEnum.OK.getState(), OK);
                return resultInfo;
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
        }else if (0 == project.getMeterials().size()){
            return PROJECT_METERIAL_NULL;
        }else {
            return PROJECT_COMPLETE;
        }
    }
}

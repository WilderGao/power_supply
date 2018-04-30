package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.dao.MeterialDao;
import com.wilder.power_supply.dao.ProjectDao;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import com.wilder.power_supply.service.ExcelService;
import com.wilder.power_supply.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/30
 * @Discription：
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ExcelServiceImpl implements ExcelService {

    //excel 的两种文件格式
    private static final String EXCEL_END_FIRST = ".xls";
    private static final String EXCEL_END_SECOND = ".xlsx";

    //工程和材料两种类名称
    private static final String PROJECT_NAME = "Project";
    private static final String METERIAL_NAME = "Meterial";

    @Autowired
    private MeterialDao meterialDao;
    @Autowired
    private ProjectDao projectDao;

    /**
     * 读取 excel 表中的内容并将内容插入到数据库中
     * @param excelPath
     * @param className
     * @throws ExcelException
     */
    @Override
    public void excelHandler(String excelPath, String className) throws ExcelException {
        if (!excelPath.endsWith(EXCEL_END_FIRST) && !excelPath.endsWith(EXCEL_END_SECOND)){
            throw new ExcelException(StatusEnum.ERROR.getState(), "文件格式不正确");

        }else if (className.equals(METERIAL_NAME)){
            //get data from meterial excel

            List<Meterial> meterials = new ArrayList<>();
            ExcelUtil.getFromExcel(excelPath, meterials, METERIAL_NAME, 2);

            if (meterials.size() == 0){
                log.error("====== 导入材料 excel 失败 ======");
                //文件可能为空的情况
                throw new ExcelException(StatusEnum.ERROR.getState(), "import meterial excel has error");
            }else {
                meterialDao.insertMeterialList(meterials);
                log.info(" ====== 插入数据库成功 ======");
            }

        }else if (className.equals(PROJECT_NAME)){

            // get data from project excel
            List<Project> projects = new ArrayList<>();
            ExcelUtil.getFromExcel(excelPath, projects, PROJECT_NAME, 1);

            if (projects.size() == 0){
                log.error("====== 导入工程 excel 失败 ======");
                throw new ExcelException(StatusEnum.ERROR.getState(), "import project excel has error");
            }else {

                //clean the same project
                List<Project> finalProjects = removeSameProject(projects);

                //insert into the project dataBase
                projectDao.insertProjectList(finalProjects);
                log.info("====== 工程插入数据库成功 ======");
            }
        }
    }

    /**
     * 除去工程名相同的工程信息
     */
    private List<Project> removeSameProject(List<Project> projects){
        List<Project> resultList = new LinkedList<>();
        Set<String> projectNameSet = new HashSet<>();

        for (Project project : projects) {
            if (!projectNameSet.contains(project.getProjectName())){
                resultList.add(project);
                projectNameSet.add(project.getProjectName());
            }
        }

        return resultList;
    }
}

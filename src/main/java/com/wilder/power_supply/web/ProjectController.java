package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import com.wilder.power_supply.service.ProjectService;
import com.wilder.power_supply.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Description：与工程有关的控制器
 */
@RestController
@RequestMapping(value = "/project")
@CrossOrigin
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    /**
     * 创建新工程
     * @param
     * @return
     */
    @PostMapping(value = "/build")
    @ResponseBody
    public ResultInfo<String> buildProject(@RequestBody Map<String, String> map, HttpServletRequest request) throws ProjectException, IOException, ExcelException, IllegalAccessException, InvocationTargetException, InstantiationException, InterruptedException {
        String sessionId = map.get("sessionId");
        String district = map.get("district");
        String batch = map.get("batch");
        String powerSupply = map.get("powerSupply");
        String projectCode = map.get("projectCode");
        String projectName = map.get("projectName");

        log.info("导出excel表格");
        Project project = new Project(district, batch, powerSupply, projectCode, projectName);

        String excelPath = request.getServletContext().getRealPath("/project/"+projectName+".xls");
        return projectService.buildProjectHandler(project, sessionId, excelPath);
    }


    @GetMapping(value = "/get")
    @ResponseBody
    public ResultInfo<List<Project>> projectList() throws ProjectException {
        return projectService.projectListHandler();
    }


    @GetMapping(value = "/detail")
    @ResponseBody
    public ResultInfo<Project> projectDetail(@RequestParam("projectId") int projectId) throws ProjectException {
        log.info("==== 查看历史工程 ====");
        return projectService.projectDetailHandler(projectId);
    }

    @PostMapping(value = "/export")
    @ResponseBody
    public ResultInfo<String> exportProject(@RequestBody Map<String, Object> map,
                                            HttpServletRequest request)
            throws ExcelException, ProjectException, IOException, InterruptedException, IllegalAccessException, InvocationTargetException, InstantiationException {
        log.info("==== 导出历史工程 ====");
        Project project = new Project();
        project.setProjectName((String) map.get("projectName"));
        project.setProjectCode((String) map.get("projectCode"));
        project.setBatch((String) map.get("batch"));
        project.setPowerSupply((String) map.get("powerSupply"));
        project.setDistrict((String) map.get("district"));

        List<Meterial> meterials = new LinkedList<>();
        List<LinkedHashMap> materialHashMap = (List<LinkedHashMap>) map.get("meterials");
        for (LinkedHashMap linkedHashMap : materialHashMap) {
            Meterial meterial = (Meterial) BeanUtil.mapToObject(linkedHashMap, Meterial.class);
            if (meterial.getNum() != null){
                meterials.add(meterial);
            }
        }

        project.setMeterials(meterials);
        String excelPathContent = request.getServletContext().getRealPath("/history/");
        return projectService.projectExport(project, excelPathContent);
    }

    @PostMapping(value = "/updatenum")
    @ResponseBody
    public ResultInfo<String> updateProjectNum(@RequestBody Map<String, Integer> map){
        log.info("修改工程材料的数量");
        Integer projectId = map.get("projectId");
        Integer materialId = map.get("meterialId");
        Integer num = map.get("num");

        if (projectId < 0 || materialId < 0 || num < 0){
            ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.ERROR.getState(), "请求参数有误");
            resultInfo.setInfo("请求参数有误");
            return resultInfo;
        }
        return projectService.updateProjectMaterialNum(projectId, materialId, num);
    }

}

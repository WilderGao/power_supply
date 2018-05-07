package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Project;
import com.wilder.power_supply.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：与工程有关的控制器
 */
@RestController
@RequestMapping(value = "/project")
@CrossOrigin
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
    public ResultInfo<String> buildProject(@RequestBody Project project) throws ProjectException {

        System.out.println(project);
        return projectService.buildProjectHandler(project);
    }


    @GetMapping(value = "/get")
    @ResponseBody
    public ResultInfo<List<Project>> projectList() throws ProjectException {
        return projectService.projectListHandler();
    }


    @GetMapping(value = "/detail")
    @ResponseBody
    public ResultInfo<Project> projectDetail(@RequestParam("projectId") int projectId) throws ProjectException {
        return projectService.projectDetailHandler(projectId);
    }

}

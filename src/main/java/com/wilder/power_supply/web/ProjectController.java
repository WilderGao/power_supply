package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.ProjectException;
import com.wilder.power_supply.model.Project;
import com.wilder.power_supply.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：与工程有关的控制器
 */
@RestController
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 创建新工程
     * @param project
     * @return
     */
    @PostMapping(value = "/project")
    @ResponseBody
    public ResultInfo buildProject(@RequestBody Project project) throws ProjectException {
        return projectService.buildProjectHandler(project);
    }

}

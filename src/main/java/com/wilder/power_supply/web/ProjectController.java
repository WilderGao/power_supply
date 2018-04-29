package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.model.Project;
import org.springframework.web.bind.annotation.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：与工程有关的控制器
 */
@RestController
@CrossOrigin
public class ProjectController {

    @PostMapping(value = "/project")
    @ResponseBody
    public ResultInfo buildProject(@RequestBody Project project){

    }
}

package com.wilder.power_supply.config;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ProjectException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：统一异常处理类
 */
@ControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(value = ProjectException.class)
    @ResponseBody
    public ResultInfo<String> jsonErrorHandler(HttpServletRequest req, ProjectException e) throws Exception {
        ResultInfo<String> r = new ResultInfo(StatusEnum.ERROR.getState(),e.getMessage());
        return r;
    }



}

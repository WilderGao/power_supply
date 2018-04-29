package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import org.springframework.web.bind.annotation.*;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：
 */
@RestController
@CrossOrigin
public class MeterialController {

    @PostMapping("/fuzzy")
    @ResponseBody
    public ResultInfo fuzzySearch(@RequestParam("meterialName")String meterialName,
                                  @RequestParam("meterialCode")String meterialCode){
        System.out.println("meterialName : "+meterialName);
        System.out.println("meterialCode : "+meterialCode);
        return null;
    }

}

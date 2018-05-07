package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.MeterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author:Wilder Gao
 * @time:2018/4/29
 * @Discription：
 */
@RestController
@RequestMapping(value = "/meterial")
@CrossOrigin
@Slf4j
public class MaterialController {

    @Autowired
    private MeterialService materialService;

    @GetMapping(value = "/fuzzy")
    @ResponseBody
    public ResultInfo<List<Meterial>> fuzzySearch(@RequestParam("meterialName")String materialName,
                                                  @RequestParam("meterialCode") String materialCode)
            throws MeterialException {

        return materialService.searchMaterial(materialCode, materialName);
    }

}

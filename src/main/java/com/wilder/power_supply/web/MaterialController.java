package com.wilder.power_supply.web;

import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.MeterialService;
import com.wilder.power_supply.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Wilder Gao
 * time:2018/4/29
 * Description：
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
        log.info(" ==== 模糊搜索材料 ==== ");

        return materialService.searchMaterial(materialCode, materialName);
    }



    @PostMapping(value = "/addmaterial")
    @ResponseBody
    public ResultInfo<String> saveMaterialBuffer(@RequestBody Map<String, Object> requestMap) throws IllegalAccessException,
            InvocationTargetException, InstantiationException {
        log.info("==== 添加材料信息 ====");
        String sessionId = (String) requestMap.get("sessionId");
        List<LinkedHashMap> linkedHashMaps = (List<LinkedHashMap>) requestMap.get("meterials");
        log.info("sessionId为："+sessionId);
        List<Meterial> materials = new ArrayList<>();
        for (LinkedHashMap hashMap : linkedHashMaps) {
            Meterial meterial = (Meterial) BeanUtil.mapToObject(hashMap,  Meterial.class);
            materials.add(meterial);
        }

        return materialService.addMaterial(sessionId, materials);
    }

    @GetMapping(value = "/show/{sessionId}")
    @ResponseBody
    public ResultInfo<List<Meterial>> showChooseMaterials(@PathVariable("sessionId")String sessionId){
        log.info("展示所有已经选择的材料: "+sessionId);
        return materialService.showChooseMaterial(sessionId);

    }

}

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

        return materialService.searchMaterial(materialCode, materialName);
    }

    /**
     * 将材料放进缓存
     * @param map
     * @return
     * @throws MeterialException
     */
    @PostMapping("/adddevice")
    @ResponseBody
    public ResultInfo<String> saveBuffer(@RequestBody Map<String, List<Meterial>> map) throws MeterialException {
        List<Meterial> meterials = map.get("materials");
        if (meterials == null || meterials.size() == 0){
            throw new MeterialException(StatusEnum.ERROR.getState(), "材料为空");
        }else {
            String uuid = UUID.randomUUID().toString();
            if (!BufferMen.projectMaterialMap.containsKey(uuid)){
                BufferMen.projectMaterialMap.put(uuid, meterials);
            }
            ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
            resultInfo.setInfo(uuid);
            return resultInfo;
        }
    }


    @PostMapping("/addmaterial")
    public ResultInfo<String> saveMaterialBuffer(@RequestBody Map<String, Object> requestMap) throws MeterialException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String sessionId = (String) requestMap.get("sessionId");
        List<LinkedHashMap> linkedHashMaps = (List<LinkedHashMap>) requestMap.get("materials");
        List<Meterial> materials = new ArrayList<>();
        for (LinkedHashMap hashMap : linkedHashMaps) {
            Meterial meterial = (Meterial) BeanUtil.mapToObject(hashMap,  Meterial.class);
            materials.add(meterial);
        }

        if (sessionId == null){
            log.info("sessionId为空，没有添加设备直接添加材料");
            String uuid = UUID.randomUUID().toString();
            if (!BufferMen.projectMaterialMap.containsKey(uuid)){
                BufferMen.projectMaterialMap.put(uuid, materials);
            }
            ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
            resultInfo.setInfo(uuid);
            return resultInfo;
        }else {
            if (BufferMen.projectMaterialMap.containsKey(sessionId)){
                //这个Id里面存在材料
                if (materials.size() != 0){
                    Map<String, List<Meterial>> map = BufferMen.projectMaterialMap;
                    map.get(sessionId).addAll(materials);

                }
                ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
                resultInfo.setInfo(sessionId);
                return resultInfo;
            }else {
                BufferMen.projectMaterialMap.put(sessionId, materials);
                ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
                resultInfo.setInfo(sessionId);
                return resultInfo;
            }
        }

    }

}

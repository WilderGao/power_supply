package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dao.MeterialDao;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.MeterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Wilder Gao
 * time:2018/4/14
 * Description：与材料有关的逻辑层
sd  */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MeterialServiceImpl implements MeterialService {

    //excel 的两种文件格式
    private static final String EXCEL_END_FIRST = ".xls";
    private static final String EXCEL_END_SECOND = ".xlsx";

    @Autowired
    private MeterialDao meterialDao;

    @Override
    public ResultInfo<List<Meterial>> searchMaterial(String materialCode, String materialName) throws MeterialException {

            List<Meterial> materials = meterialDao.selectMeterialLike(materialName, materialCode);
            if (materials.size() == 0){
                log.error(" ====== 查询不到相关的材料信息 ====== ");
                ResultInfo<List<Meterial>> resultInfo = new ResultInfo<>(StatusEnum.ERROR.getState(), "查询不到相关的材料信息");
                return resultInfo;

            }else {
                log.info(" ====== select success ======");
                ResultInfo<List<Meterial>> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "OK");
                resultInfo.setInfo(materials);
                return resultInfo;

            }

    }

    @Override
    public ResultInfo<String> addMaterial(String sessionId, List<Meterial> materials) {
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

    @Override
    public ResultInfo<List<Meterial>> showChooseMaterial(String sessionId) {
        if (!sessionId.isEmpty()) {
            List<Meterial> meterialList = new LinkedList<>();
            if (BufferMen.userMap.containsKey(sessionId)) {
                List<Device> deviceMap = BufferMen.userMap.get(sessionId);
                deviceMap.forEach((v)-> meterialList.addAll(v.getMeterials()));
            }
            if (BufferMen.projectMaterialMap.containsKey(sessionId)){
                meterialList.addAll(BufferMen.projectMaterialMap.get(sessionId));
            }
            ResultInfo<List<Meterial>> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "显示成功");
            resultInfo.setInfo(meterialList);
            return resultInfo;
        }else {
            return new ResultInfo<>(StatusEnum.PATAMETER_ERROR.getState(), "sessionId 为空");
        }
    }
}


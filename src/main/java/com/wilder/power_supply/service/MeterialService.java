package com.wilder.power_supply.service;

import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.model.Meterial;

import java.util.List;

/**
 * @author Wilder Gao
 * time:2018/4/14
 * Description：与材料有关的服务类
 */
public interface MeterialService {

    /**
     * 按材料编码或者材料名获取对应的材料信息
     * @param materialCode 材料编码
     * @param materialName  材料名称
     * @return
     * @throws MeterialException
     */
    ResultInfo<List<Meterial>> searchMaterial(String materialCode, String materialName) throws MeterialException;
}

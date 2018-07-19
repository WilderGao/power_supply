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

    /**
     * 添加材料信息
     * @param sessionId sessionId
     * @param materials 材料集合
     * @return 结果集
     */
    ResultInfo<String> addMaterial(String sessionId, List<Meterial> materials);


    /**
     * 展示已经选择的所有材料
     * @param sessionId 指定用户
     * @return 查询结果集
     */
    ResultInfo<List<Meterial>> showChooseMaterial(String sessionId);
}

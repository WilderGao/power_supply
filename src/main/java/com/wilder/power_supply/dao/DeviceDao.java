package com.wilder.power_supply.dao;

import com.wilder.power_supply.model.Meterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/5/1
 * @Discription：设备处理类
 */
@Mapper
@Repository
public interface DeviceDao {
    /**
     * 根据工程Id查找有关的材料信息
     * @param deviceId
     * @return
     */
    List<Meterial> getMeterialsById(@Param("deviceId")int deviceId);
}

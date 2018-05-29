package com.wilder.power_supply.dao;

import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.model.Meterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author Wilder Gao
 * time:2018/5/1
 * Description：设备处理类
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


    /**
     * 获得所有设备
     * @return
     */
    List<Device> getDeviceList();


    /**
     * 通过设备Id获取设备名
     * @param deviceId
     * @return
     */
    String getDeviceName(@Param("deviceId")int deviceId);
}

package com.wilder.power_supply.service;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.model.Device;


/**
 * @author:Wilder Gao
 * @time:2018/5/1
 * @Discription：与设备有关的逻辑接口
 */
public interface DeviceService {

    /**
     * 根据设备Id获取设备的详细信息
     * @return
     */
    ResultInfo<Device> deviceDetailHandler(int deviceId, String deviceName) throws DeviceException;
}

package com.wilder.power_supply.service;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.model.Device;

import java.io.IOException;
import java.util.List;


/**
 * @author Wilder Gao
 * time:2018/5/1
 * description: 与设备有关的逻辑接口
 *
 */
public interface DeviceService {


    /**
     * 根据设备Id获取设备的详细信息
     * @param deviceId      设备Id
     * @param deviceName    设备名称
     * @return  设备详情
     * @throws DeviceException
     */
    ResultInfo<Device> deviceDetailHandler(int deviceId) throws DeviceException;


    /**
     * 导出excel表格，返回对应的连接
     * @param device 设备名称
     * @param excelPath 导出excel路径
     * @return
     * @throws ExcelException
     */
    ResultInfo<String> deportDevice(Device device, String excelPath) throws ExcelException, IOException, DeviceException, InterruptedException;


    /**
     * 查看设备列表
     * @return
     */
    ResultInfo<List<Device>> deviceList();

}

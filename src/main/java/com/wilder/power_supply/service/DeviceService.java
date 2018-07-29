package com.wilder.power_supply.service;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.model.Device;
import org.omg.PortableInterceptor.RequestInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


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
     * @return  设备详情
     * @throws DeviceException
     */
    ResultInfo<Device> deviceDetailHandler(int deviceId) throws DeviceException;


    /**
     * 导出设备
     * @param device
     * @param excelPath
     * @return
     * @throws ExcelException
     * @throws IOException
     * @throws DeviceException
     * @throws InterruptedException
     */
    ResultInfo<String> deportDevice(Device device, String excelPath) throws ExcelException, IOException, DeviceException, InterruptedException;


    /**
     * 查看设备列表
     * @return
     */
    ResultInfo<List<Device>> deviceList();

    /**
     * 删除已经选择的设备信息
     * @param sessionId 对应操作者
     * @param deviceId 设备名称
     * @return
     */
    ResultInfo<String> deleteChooseDevice(String sessionId, int deviceId);

    /**
     * 保存已经选择的设备信息
     * @param map 请求的键值对
     * @return  结果集合
     */
    ResultInfo<String> saveSelectedDevice(Map<String, Object> map) throws IllegalAccessException, InvocationTargetException, InstantiationException;

}

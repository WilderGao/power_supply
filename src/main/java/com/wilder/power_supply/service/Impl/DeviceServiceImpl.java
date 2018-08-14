package com.wilder.power_supply.service.Impl;
import com.google.gson.Gson;
import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dao.DeviceDao;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.DeviceService;
import com.wilder.power_supply.utils.BeanUtil;
import com.wilder.power_supply.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Wilder Gao
 * time:2018/5/1
 * Description：与设备有关的逻辑接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DeviceServiceImpl implements DeviceService {
    private static String url = "http://120.77.38.183/device/";

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public ResultInfo<Device> deviceDetailHandler(int deviceId) throws DeviceException {
        if (deviceId <= 0 ){
            log.error("获取的 设备Id 有误");
            throw new DeviceException(StatusEnum.ERROR.getState(), "设备Id有误");
        }else {
            //获取设备的详细材料信息
            List<Meterial> deviceDetail = deviceDao.getMeterialsById(deviceId);
            if (0 == deviceDetail.size()){
                throw new DeviceException(StatusEnum.ERROR.getState(), "材料信息结果为空");
            }else {
                String deviceName = deviceDao.getDeviceName(deviceId);
                if (null == deviceName){
                    throw new DeviceException(StatusEnum.ERROR.getState(), "设备不存在");
                }
                Device device = new Device(deviceId, deviceName, deviceDetail);
                ResultInfo resultInfo =  new ResultInfo<>(StatusEnum.OK.getState(), "OK", device);
                Gson gson = new Gson();
                System.out.println(gson.toJson(resultInfo));

                return resultInfo;
            }
        }
    }

    @Override
    public ResultInfo<String> deportDevice(Device device, String excelPath) throws ExcelException, IOException, DeviceException, InterruptedException {

        //首先判断信息是否为空
        if (null == device || null == device.getDeviceName() || 0 == device.getMeterials().size()){
            throw new ExcelException(StatusEnum.ERROR.getState(), "生成excel表时提交内容不充足");
        }else {

            //导出 excel 表不需要对数据库进行操作
            String excelPathFinal = ExcelUtil.exportExcel(device.getMeterials(), excelPath);
            if (excelPathFinal == null) {
                throw new DeviceException(StatusEnum.ERROR.getState(), "导出excel路径有误");
            }

            ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "OK");
            resultInfo.setInfo(url+device.getDeviceName()+".xls");
            Thread.sleep(3000);

            return resultInfo ;
        }
    }

    @Override
    public ResultInfo<List<Device>> deviceList(){
        List<Device> devices = deviceDao.getDeviceList();
        if (null == devices || 0 == devices.size()){
            log.info("没有设备信息");
            return new ResultInfo<>(StatusEnum.ERROR.getState(), "没有设备信息");
        }else {
            ResultInfo<List<Device>> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "OK");
            resultInfo.setInfo(devices);
            return resultInfo;
        }
    }

    @Override
    public ResultInfo<String> deleteChooseDevice(String sessionId, int deviceId) {
        if (sessionId.isEmpty() ){
            log.info("传入的sessionId 和 deviceName 为空");
            return new ResultInfo<>(StatusEnum.PATAMETER_ERROR.getState(), "传入参数为空");
        }else {
            int deleteIndex = -1;
            Map<String, List<Device>> userMap = BufferMen.userMap;
            if (userMap.containsKey(sessionId)){
                //将对应的设备信息删除
                List<Device> devices = userMap.get(sessionId);
                for (Device device : devices) {
                    if (device.getDeviceId() == deviceId ){
                        deleteIndex = devices.indexOf(device);
                        log.info("准备删除的index为："+deleteIndex);
                        break;
                    }
                }
                //判断是否有要删除的设备
                if (deleteIndex != -1){
                    //认为有东西要删除
                    devices.remove(deleteIndex);
                    log.info("删除成功");
                }else{
                    log.info("不存在这个材料，删除失败");
                }
            }
            return new ResultInfo<>(StatusEnum.OK.getState(), "操作成功");

        }
    }

    @Override
    public ResultInfo<String> saveSelectedDevice(Map<String, Object> requestMap) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        log.info("===== 将设备中的材料放入缓存 =====");
        if (requestMap == null || requestMap.size() == 0){
            return new ResultInfo<>(StatusEnum.ERROR.getState(), "传入参数有误");
        }
        String sessionId = (String) requestMap.get("sessionId");
        log.info("====sessionId为："+sessionId);
        List<Device> devices = new LinkedList<>();
        List<LinkedHashMap> requestList = (List<LinkedHashMap>) requestMap.get("devices");

        for (LinkedHashMap hashMap : requestList) {
            List<Meterial> meterials = new LinkedList<>();
            List<LinkedHashMap> materialHashMap = (List<LinkedHashMap>) hashMap.get("meterials");
            for (LinkedHashMap linkedHashMap : materialHashMap) {
                meterials.add((Meterial) BeanUtil.mapToObject(linkedHashMap, Meterial.class));
            }
            int deviceId = (int) hashMap.get("deviceId");
            String deviceName = (String) hashMap.get("deviceName");
            Device device = new Device(deviceId, deviceName, meterials);
            devices.add(device);
        }

        if (sessionId == null || sessionId.isEmpty()){
            log.info("sessionId为空，从来没有添加过");
            String uuid = UUID.randomUUID().toString();
            if (!BufferMen.userMap.containsKey(uuid)){
                log.info("不存在该操作者的信息......");
                BufferMen.userMap.put(uuid, devices);
            }
            ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
            resultInfo.setInfo(uuid);
            return resultInfo;
        }else {
            log.info("说明存在sessionId");
            if (devices.size() != 0){
                if (BufferMen.userMap.containsKey(sessionId)) {
                    BufferMen.userMap.get(sessionId).addAll(devices);
                }else {
                    BufferMen.userMap.put(sessionId, devices);
                }
                ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
                resultInfo.setInfo(sessionId);
                log.info("当前用户的设备信息为:"+BufferMen.userMap.get(sessionId));
                return resultInfo;
            }
        }
        return new ResultInfo<>(StatusEnum.ERROR.getState(),"服务器出现错误");

    }

}

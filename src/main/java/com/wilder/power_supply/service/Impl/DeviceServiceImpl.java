package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.dao.DeviceDao;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/5/1
 * @Discription：与设备有关的逻辑接口实现类
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public ResultInfo<Device> deviceDetailHandler(int deviceId, String deviceName) throws DeviceException {
        if (deviceId <= 0 ){
            log.error("获取的 设备Id 有误");
            throw new DeviceException(StatusEnum.ERROR.getState(), "设备Id有误");
        }else {

            //获取设备的详细材料信息
            List<Meterial> deviceDetail = deviceDao.getMeterialsById(deviceId);

            if (0 == deviceDetail.size()){
                throw new DeviceException(StatusEnum.ERROR.getState(), "材料信息结果为空");
            }else {
                Device device = new Device(deviceId, deviceName, deviceDetail);
                ResultInfo<Device> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "OK", device);
                return resultInfo;
            }
        }
    }


}

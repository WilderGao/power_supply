package com.wilder.power_supply.web;

import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.DeviceService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Wilder Gao
 * time 2018/5/4
 * Description：
 */
@RestController
@RequestMapping(value = "/device")
@CrossOrigin
@Log
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    @GetMapping(value = "/get")
    public ResultInfo<List<Device>> deviceList(){
        log.info(" ==== 获得设备列表 ====");
        return deviceService.deviceList();
    }


    @PostMapping(value = "/export")
    @ResponseBody
    public ResultInfo<String> deportDeviceExcel(@RequestBody Device device, HttpServletRequest request) throws ExcelException, DeviceException, IOException, InterruptedException {
        log.info(" ==== 导出设备为excel ====");
        String excelPath = request.getServletContext().getRealPath("/device/" + device.getDeviceName()+".xls");
        return deviceService.deportDevice(device, excelPath);

    }


    @GetMapping(value = "/detail")
    @ResponseBody
    public ResultInfo<Device> deviceDetail(@RequestParam("deviceId")int deviceId)
            throws DeviceException {
        log.info("查看 设备");
        return deviceService.deviceDetailHandler(deviceId);
    }

    /**
     * 将设备放进缓存
     * @param map
     * @return
     * @throws MeterialException
     */
    @PostMapping("/adddevice")
    @ResponseBody
    public ResultInfo<String> saveBuffer(@RequestBody Map<String, List<Meterial>> map) throws MeterialException {
        log.info("===== 将设备中的材料放入缓存 =====");
        if (map == null || map.size() == 0){
            throw new MeterialException(StatusEnum.ERROR.getState(), "材料为空");
        }else {
            String uuid = UUID.randomUUID().toString();
            if (!BufferMen.userMap.containsKey(uuid)){
                log.info("不存在改操作者的信息......");
                BufferMen.userMap.put(uuid, map);
            }
            ResultInfo<String> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "保存成功");
            resultInfo.setInfo(uuid);
            return resultInfo;
        }
    }


    @GetMapping(value = "/delete/{sessionId}/{deviceName}")
    @ResponseBody
    public ResultInfo deleteChooseDevice(@PathVariable("sessionId")String sessionId,
                                         @PathVariable("deviceName")String deviceName){
        log.info(sessionId + "对应的账号要删除"+deviceName+"这个设备");
        return deviceService.deleteChooseDevice(sessionId, deviceName);
    }
}

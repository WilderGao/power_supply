package com.wilder.power_supply.web;

import com.wilder.power_supply.buffer.BufferMen;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.service.DeviceService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

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
        log.info("查看设备"+deviceId);
        return deviceService.deviceDetailHandler(deviceId);
    }

    /**
     * 将设备放进缓存
     * @param requestMap 设备集合
     * @return
     */
    @PostMapping("/adddevice")
    @ResponseBody
    public ResultInfo<String> saveBuffer(@RequestBody Map<String, Object> requestMap)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return deviceService.saveSelectedDevice(requestMap);
    }


    @GetMapping(value = "/delete/{sessionId}/{deviceId}")
    @ResponseBody
    public ResultInfo<String> deleteChooseDevice(@PathVariable("sessionId")String sessionId,
                                         @PathVariable("deviceId")int deviceId){
        log.info(sessionId + "对应的账号要删除"+deviceId+"这个设备");
        return deviceService.deleteChooseDevice(sessionId, deviceId);
    }

    /**
     * 得到已经选择的设备信息
     * @param sessionId 用户Id
     * @return  结果集
     */
    @GetMapping(value = "/selected/{sessionId}")
    public ResultInfo<List<Device>> getChooseDevice(@PathVariable("sessionId")String sessionId){
        if (sessionId == null || sessionId.isEmpty()){
            return new ResultInfo<>(StatusEnum.ERROR.getState(), "传入参数有误");
        }else {
            ResultInfo<List<Device>> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "操作成功");
            resultInfo.setInfo(BufferMen.userMap.get(sessionId));

            return resultInfo;
        }
    }
}

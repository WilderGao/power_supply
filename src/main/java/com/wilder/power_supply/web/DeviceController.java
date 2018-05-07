package com.wilder.power_supply.web;

import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.exception.DeviceException;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/5/4
 * @Discription：
 */
@RestController
@RequestMapping(value = "/device")
@CrossOrigin
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    @GetMapping(value = "/get")
    public ResultInfo<List<Device>> deviceList(){
        return deviceService.deviceList();
    }


    @PostMapping(value = "/deport")
    @ResponseBody
    public ResultInfo<String> deportDeviceExcel(@RequestBody Device device, HttpServletRequest request) throws ExcelException, DeviceException, IOException {
        String excelPath = request.getServletContext().getRealPath("/device/" + device.getDeviceName()+".xls");
        ResultInfo<String> resultInfo = deviceService.deportDevice(device, excelPath);

        return resultInfo;
    }


    @GetMapping(value = "/detail")
    @ResponseBody
    public ResultInfo<Device> deviceDetail(@RequestParam("deviceId")int deviceId) throws DeviceException {

        return deviceService.deviceDetailHandler(deviceId);
    }
}

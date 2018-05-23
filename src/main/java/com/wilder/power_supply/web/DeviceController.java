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

import javax.servlet.ServletContext;
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
        return deviceService.deviceList();
    }


    @PostMapping(value = "/export")
    @ResponseBody
    public ResultInfo<String> deportDeviceExcel(@RequestBody Device device, HttpServletRequest request) throws ExcelException, DeviceException, IOException, InterruptedException {
        String excelPath = request.getServletContext().getRealPath("/device/" + device.getDeviceName()+".xls");
        return deviceService.deportDevice(device, excelPath);

    }


    @GetMapping(value = "/detail")
    @ResponseBody
    public ResultInfo<Device> deviceDetail(@RequestParam("deviceId")int deviceId) throws DeviceException {

        return deviceService.deviceDetailHandler(deviceId);
    }

}

package com.wilder.power_supply.web;

import com.wilder.power_supply.model.Device;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public RequestInfo<List<Device>> diviceList(){

    }
}

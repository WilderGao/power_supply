package com.wilder.power_supply.buffer;

import com.wilder.power_supply.model.Device;
import com.wilder.power_supply.model.Meterial;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wilder Gao
 * time:2018/5/23
 * description：
 * motto: All efforts are not in vain
 */
public final class BufferMen {

    /**
     * 用户和材料对应的缓存，需要和下方设备对应缓存的 UUID 相对应
     */
    public static Map<String, List<Meterial>> projectMaterialMap = new Hashtable<>(10);

    /**
     * 用户和用户选择的设备对应缓存
     */
    public static Map<String, List<Device>> userMap = new Hashtable<>(10);


}

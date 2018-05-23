package com.wilder.power_supply.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Wilder Gao
 * time:2018/5/23
 * description：map 与 对象的转化
 * motto: All efforts are not in vain
 */
@Component
public class BeanUtil {

    /**
     * 将map转为对象
     * @param map
     * @param beanClass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();

        BeanUtils.populate(obj, map);

        return obj;
    }
}

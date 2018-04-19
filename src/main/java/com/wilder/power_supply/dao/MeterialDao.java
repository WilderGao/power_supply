package com.wilder.power_supply.dao;

import com.wilder.power_supply.model.Meterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：
 */
@Mapper
@Repository
@Scope("prototype")
public interface MeterialDao {

    /**
     * 将从excel读取到的数据批量插入到数据库表中
     * @param list
     * @return
     */
    int insertMeterialList(@Param("list") List<Meterial> list);

    /**
     * 模糊材料名或者材料编号模糊匹配
     * @param meterialName
     * @param meterialCode
     * @return
     */
    List<Meterial> selectMeterialLike(@Param("meterialName")String meterialName ,
                                      @Param("meterialCode")String meterialCode);
}

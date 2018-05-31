package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.dao.MeterialDao;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.enums.StatusStatementEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.MeterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wilder Gao
 * time:2018/4/14
 * Description：与材料有关的逻辑层
sd  */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MeterialServiceImpl implements MeterialService {

    //excel 的两种文件格式
    private static final String EXCEL_END_FIRST = ".xls";
    private static final String EXCEL_END_SECOND = ".xlsx";

    @Autowired
    private MeterialDao meterialDao;

    @Override
    public ResultInfo<List<Meterial>> searchMaterial(String materialCode, String materialName) throws MeterialException {

            List<Meterial> materials = meterialDao.selectMeterialLike(materialName, materialCode);
            if (materials.size() == 0){
                log.error(" ====== 查询不到相关的材料信息 ====== ");
                ResultInfo<List<Meterial>> resultInfo = new ResultInfo<>(StatusEnum.ERROR.getState(), "查询不到相关的材料信息");
                return resultInfo;

            }else {
                log.info(" ====== select success ======");
                ResultInfo<List<Meterial>> resultInfo = new ResultInfo<>(StatusEnum.OK.getState(), "OK");
                resultInfo.setInfo(materials);
                return resultInfo;

            }

    }
}


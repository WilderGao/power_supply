package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.dao.MeterialDao;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.dto.ResultInfo;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.MeterialService;
import com.wilder.power_supply.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：与材料有关的逻辑层
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
    public void meterialHandler(String excelPath) throws ExcelException {
        if (!excelPath.endsWith(EXCEL_END_FIRST) && !excelPath.endsWith(EXCEL_END_SECOND)){
            throw new ExcelException(StatusEnum.ERROR.getState(), "文件格式不正确");
        }else {
            List<Meterial> meterials = new ArrayList<>();
            ExcelUtil.getMeterialFromExcel(excelPath, meterials);
            if (meterials.size() == 0){
                throw new ExcelException(StatusEnum.ERROR.getState(), "excel 文件中没有数据");
            }
            meterialDao.insertMeterialList(meterials);
            log.info("插入数据库成功！！！");
        }
    }

    @Override
    public ResultInfo<List<Meterial>> searchMeterial(String meterialCode, String meterialName) throws MeterialException {
        ResultInfo<List<Meterial>> resultInfo = new ResultInfo();

        if (meterialCode == null && meterialName == null){
            log.error(" ====== all parameter are null ====== ");
            resultInfo.setStatus(StatusEnum.PATAMETER_ERROR.getState());
                throw new MeterialException(StatusEnum.PATAMETER_ERROR.getState(), "传入参数有误");
        }else {

            List<Meterial> meterials = meterialDao.selectMeterialLike(meterialName, meterialCode);
            resultInfo.setStatus(StatusEnum.OK.getState());
            resultInfo.setInfo(meterials);
            log.info(" ====== select success ");
        }

        return resultInfo;
    }
}

package com.wilder.power_supply.service.Impl;

import com.wilder.power_supply.dao.MeterialDao;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.MeterialException;
import com.wilder.power_supply.model.FeedBack;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.service.MeterialService;
import com.wilder.power_supply.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：
sd  */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MeterialServiceImpl implements MeterialService {
    private final Logger logger = LoggerFactory.getLogger(MeterialServiceImpl.class);
    @Autowired
    private MeterialDao meterialDao;

    @Override
    public void meterialHandler(String excelPath) throws Exception {
        if (!excelPath.endsWith(".xls") && !excelPath.endsWith(".xlsx")){
            throw new Exception("文件格式不正确");
        }else {
            List<Meterial> meterials = new ArrayList<>();
            ExcelUtil.getMeterialFromExcel(excelPath, meterials);
            if (meterials.size() == 0){
                throw new Exception("excel 文件中没有数据");
            }
            meterialDao.insertMeterialList(meterials);
            logger.info("插入数据库成功！！！");
        }
    }

    @Override
    public FeedBack<List<Meterial>> searchMeterial(String meterialCode, String meterialName){
        FeedBack<List<Meterial>> feedBack = new FeedBack<>();
        if (meterialCode == null && meterialName == null){
            feedBack.setStatus(StatusEnum.PATAMETER_ERROR.getState());
            try {
                throw new MeterialException(StatusEnum.PATAMETER_ERROR.getState(), "传入参数有误");
            } catch (MeterialException e) {
                e.printStackTrace();
            }
        }else {
            List<Meterial> meterials = meterialDao.selectMeterialLike(meterialName, meterialCode);
            feedBack.setStatus(StatusEnum.OK.getState());
            feedBack.setInfo(meterials);
        }
        return feedBack;
    }
}

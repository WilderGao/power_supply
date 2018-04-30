package com.wilder.power_supply.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener {

    private List datas = new ArrayList<>();


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        if (o instanceof Project || o instanceof Meterial){
            datas.add(o);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("====== 导入完成 ======");
    }

    public void setDatas(List datas){
        this.datas = datas;
    }
}

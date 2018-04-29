package com.wilder.power_supply.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wilder.power_supply.model.Meterial;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<Meterial> {

    private List<Meterial> datas = new ArrayList<>();
    @Override
    public void invoke(Meterial meterial, AnalysisContext analysisContext) {
        datas.add(meterial);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("====== 导入完成 ======");
    }

    public void setDatas(List<Meterial> datas){
        this.datas = datas;
    }
}

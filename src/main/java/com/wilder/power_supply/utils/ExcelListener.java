package com.wilder.power_supply.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wilder.power_supply.model.Meterial;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：
 */
public class ExcelListener extends AnalysisEventListener<Meterial> {

    private List<Meterial> datas = new ArrayList<>();
    @Override
    public void invoke(Meterial meterial, AnalysisContext analysisContext) {
        datas.add(meterial);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        for (Meterial data : datas) {
//            System.out.println(data);
//        }
        System.out.println(datas.size());
    }

    public void setDatas(List<Meterial> datas){
        this.datas = datas;
    }
}

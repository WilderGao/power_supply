package com.wilder.power_supply.utils;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 * @author:Wilder Gao
 * @time:2018/4/14
 * @Discription：操作excel表格工具类
 */
@Slf4j
public class ExcelUtil {
    private static String meterial = "Meterial";
    private static String project = "Project";

    /**
     * 操作材料 excel 表或者工程表并将数据导入到数据库
     * @param excelPath
     * @param list
     */
    public static void getFromExcel(String excelPath, List list, String className, int sheetNo){
            InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            ExcelListener listener = new ExcelListener();
            listener.setDatas(list);

            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);

            if (meterial.equals(className)) {
                reader.read(new Sheet(sheetNo, 2, Meterial.class));

            }else if (project.equals(className)){
                reader.read(new Sheet(sheetNo, 2, Project.class));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

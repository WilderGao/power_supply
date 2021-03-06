package com.wilder.power_supply.utils;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.wilder.power_supply.enums.StatusEnum;
import com.wilder.power_supply.exception.ExcelException;
import com.wilder.power_supply.model.Meterial;
import com.wilder.power_supply.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Wilder Gao
 * time:2018/4/14
 * Description：操作excel表格工具类
 */
@Slf4j
public class ExcelUtil {
    /**
     * 两种不同的表类型
     */
    private static String meterial = "Meterial";
    private static String project = "Project";

    /**
     * excel 目录
     */
    private static List<String> contents = Arrays.asList("   物资编码    ", "    物资名称    ", "    规格型号    ",
            "单位", "单价（隐藏列）", "是否业扩储备物资", "  备注1（隐藏列）  ", "  数量  ");


    private static List<String> projectContents = Arrays.asList(
            "   区局   ", "批次", " 供电所 ", "项目编号", "  工程名称  ","  序号 ",
            "   材料编码   ", "   材料名称   ", "  规格型号  ", "单位", "数量", "单价", "是否业扩","备注信息"
    );

    private static String url = "http://172.22.26.147/";

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


    /**
     * 将材料内容导出为excel表
     * @param materials 材料详情
     * @return
     */
    public static String exportExcel(List<Meterial> materials, String excelPath) throws ExcelException, IOException {
        if (materials.size() == 0){
            throw new ExcelException(StatusEnum.ERROR.getState(), " 材料详情为空 ");
        }else {
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建excel表格

            HSSFSheet spreadSheet = workbook.createSheet("day");

            //设置单元格大小
            spreadSheet.setDefaultColumnWidth(20);
            spreadSheet.setDefaultRowHeight((short) (30*20));


            int rowId = 0 , cellId = 0;
            //创建第一行，第一行是类型行
            HSSFRow row = spreadSheet.createRow(rowId++);
            //设置字体大小和颜色
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("微软雅黑");
            font.setBold(true);

            //设置单元格格式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);

            for (String content : contents) {
                Cell cell = row.createCell(cellId++);
                cell.setCellValue(content);
            }

            style.setFont(font);
            int cellNum;
            for (Meterial material : materials) {
                cellNum = 0;
                row = spreadSheet.createRow(rowId ++);

                //将材料的信息插入 excel 单元格中
                Cell cellCode = row.createCell(cellNum ++);
                cellCode.setCellValue(material.getMeterialCode());

                Cell cellName = row.createCell(cellNum++);
                cellName.setCellValue(material.getMeterialName());

                Cell cellModel = row.createCell(cellNum++);
                cellModel.setCellValue(material.getMeterialModel());

                Cell cellUnit = row.createCell(cellNum++);
                cellUnit.setCellValue(material.getMeterialUnit());

                Cell cellPrice = row.createCell(cellNum++);
                cellPrice.setCellValue(material.getMeterialPrice());

                Cell cellCheck = row.createCell(cellNum++);
                cellCheck.setCellValue(material.getMeterialCheck());

                Cell cellAttention = row.createCell(cellNum++);
                cellAttention.setCellValue(material.getMeterialAttention());

                Cell cellCount = row.createCell(cellNum);
                cellCount.setCellValue(material.getNum());

            }

            FileOutputStream outputStream = new FileOutputStream(
                    new File(excelPath)
            );

            workbook.write(outputStream);
            outputStream.close();
            log.info(" 导出完成 ");

            return excelPath;
        }
    }

    /**
     * 将工程导出为excel表
     * @param project 工程
     * @param excelPath excel路径
     * @param ifNew 是否为新工程
     * @return  excel路径
     */
    public static String exportProject(Project project, String excelPath, boolean ifNew) throws ExcelException, IOException {
        if (project.getMeterials().size() == 0){
            throw new ExcelException(StatusEnum.ERROR.getState(), " 材料详情为空 ");
        }else {
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建excel表格
            HSSFSheet spreadSheet = workbook.createSheet(project.getProjectName());

            //设置单元格大小
            spreadSheet.setDefaultColumnWidth(20);
            spreadSheet.setDefaultRowHeight((short) (30*20));


            int rowId = 0 , cellId = 0;
            //创建第一行，第一行是类型行
            HSSFRow row = spreadSheet.createRow(rowId++);
            //设置字体大小和颜色
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("微软雅黑");
            font.setBold(true);

            //设置单元格格式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setFont(font);

            for (String content : projectContents) {
                Cell cell = row.createCell(cellId++);
                cell.setCellValue(content);
            }

            style.setFont(font);
            int cellNum;

            List<Meterial> meterials = project.getMeterials();
            for (int i = 0; i< meterials.size(); i++) {
                cellNum = 0;
                row = spreadSheet.createRow(rowId ++);

                //将材料的信息插入 excel 单元格中
                Cell cellProjectCode = row.createCell(cellNum ++);
                cellProjectCode.setCellValue(project.getDistrict());

                Cell cellProjectName = row.createCell(cellNum++);
                cellProjectName.setCellValue(project.getBatch());

                Cell cellProjectDistinct = row.createCell(cellNum++);
                cellProjectDistinct.setCellValue(project.getPowerSupply());

                Cell cellProjectBatch = row.createCell(cellNum++);
                cellProjectBatch.setCellValue(project.getProjectCode());

                Cell cellProjectSupply = row.createCell(cellNum++);
                cellProjectSupply.setCellValue(project.getProjectName());

                Cell cellIndex = row.createCell(cellNum++);
                cellIndex.setCellValue(i+1);

                Cell cellCode = row.createCell(cellNum++);
                cellCode.setCellValue(meterials.get(i).getMeterialCode());

                Cell cellName = row.createCell(cellNum++);
                cellName.setCellValue(meterials.get(i).getMeterialName());

                Cell cellModel = row.createCell(cellNum++);
                cellModel.setCellValue(meterials.get(i).getMeterialModel());

                Cell cellUnit = row.createCell(cellNum++);
                cellUnit.setCellValue(meterials.get(i).getMeterialUnit());

                Cell cellNumber = row.createCell(cellNum++);
                cellNumber.setCellValue(meterials.get(i).getNum());

                Cell cellPrice = row.createCell(cellNum++);
                cellPrice.setCellValue(meterials.get(i).getMeterialPrice());

                Cell cellCheck = row.createCell(cellNum++);
                cellCheck.setCellValue(meterials.get(i).getMeterialCheck());

                Cell cellAttention = row.createCell(cellNum);
                cellAttention.setCellValue(meterials.get(i).getMeterialAttention());

            }

            File file = new File(excelPath);

            if (!file.exists()){
                boolean result = file.createNewFile();
                if (result == true){
                    log.info("文件创建成功");
                }
            }
            FileOutputStream outputStream = new FileOutputStream(file);

            workbook.write(outputStream);
            outputStream.close();
            log.info(" 导出完成 ");

            if (ifNew) {
                return url + "project/"+project.getProjectName() + ".xls";
            }else {
                return url + "history/"+project.getProjectName() + ".xls";
            }
        }
    }

}

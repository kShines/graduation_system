package com.example.managementsystem.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.util.List;

public class ExcelUtil {
    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @return
     */
    public static XSSFWorkbook getXSSFWorkbook(String sheetName, List<String> title, List<List<String>> values) {

        // 第一步，创建一个XSSFWorkbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格样式，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();

        //声明单元格
        XSSFCell cell = null;

        //创建标题
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);
        for (int i = 0; i < title.size(); i++) {
            //创建一个单元格
            cell = row.createCell(i + 1);
            //给单元格赋值
            cell.setCellValue(title.get(i));
            //给单元格设置样式
            cell.setCellStyle(style);
        }

        //创建内容
        if(values.size() != 0) {
            if (values != null && values.get(0).size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    //从第二行开始创建数据填充的行，下标为1
                    row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(i + 1);
                    for (int j = 0; j < values.get(i).size(); j++) {
                        //将内容按顺序赋给对应的列对象
                        row.createCell(j + 1).setCellValue(values.get(i).get(j));
                    }
                }
            }
        }
        return wb;
    }
}
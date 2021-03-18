package com.example.managementsystem.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileUtil {
    public static ArrayList<ArrayList<String>> analysis(MultipartFile file) {
        ArrayList<ArrayList<String>> row = new ArrayList<>();
        //获取文件名称
        String fileName = file.getOriginalFilename();

        try {
            //获取输入流
            InputStream in = file.getInputStream();
            //判断excel版本
            XSSFWorkbook workbook = null;
            workbook = new XSSFWorkbook(in);

            //获取第一张工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            //从第二行开始获取
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                //循环获取工作表的每一行
                XSSFRow sheetRow = sheet.getRow(i);
                //循环获取每一列
                ArrayList<String> cell = new ArrayList<>();
                for (int j = 0; j < sheetRow.getPhysicalNumberOfCells(); j++) {
                    //将每一个单元格的值装入列集合
                    sheetRow.getCell(j).setCellType(CellType.STRING);
                    cell.add(sheetRow.getCell(j).getStringCellValue());
                }
                //将装有每一列的集合装入大集合
                row.add(cell);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("===================未找到文件======================");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("===================上传失败======================");
        }
        return row;
    }
}

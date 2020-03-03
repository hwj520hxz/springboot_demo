package com.boss.demo.commons.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @author ：hwj
 * @version 版本号：V1.0
 * @Description ：
 */
public class ExportExcelUtil {
    /**
     *
     * @param title 标题
     * @param headers  表头
     * @param values  表中元素
     * headerStyle 表头样式
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String title, String headers[], String [][] values){

        //创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        //在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet hssfSheet = hssfWorkbook.createSheet(title);

        //创建标题合并行
        hssfSheet.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)headers.length - 1));

        //设置表格默认列宽度
        hssfSheet.setDefaultColumnWidth(20);

        //设置标题样式
        HSSFCellStyle titleStyle = hssfWorkbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);   //设置居中样式
        titleStyle.setFillBackgroundColor(IndexedColors.YELLOW.index); //背景色
        titleStyle.setBorderBottom(BorderStyle.THIN); //下边框
        titleStyle.setBorderLeft(BorderStyle.THIN);  //左边框
        titleStyle.setBorderRight(BorderStyle.THIN);  //右边框
        titleStyle.setBorderTop(BorderStyle.THIN); //上边框
        //设置标题字体
        Font titleFont = hssfWorkbook.createFont();
        titleFont.setFontName("华文行楷");//设置字体名称
        titleFont.setFontHeightInPoints((short) 25); //设置字号
        titleFont.setBold(true);//设置是否加粗
        titleFont.setColor(IndexedColors.RED.index);//设置字体颜色
        titleStyle.setFont(titleFont);

        //设置值表头样式 设置表头居中
        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);   //设置居中样式
        headerStyle.setBorderBottom(BorderStyle.THIN); //下边框
        headerStyle.setBorderLeft(BorderStyle.THIN);  //左边框
        headerStyle.setBorderRight(BorderStyle.THIN);  //右边框
        headerStyle.setBorderTop(BorderStyle.THIN); //上边框
        headerStyle.setFillBackgroundColor(IndexedColors.BLUE.index); //背景色

        //设置表头字体
        Font headerFont = hssfWorkbook.createFont();
        headerFont.setFontName("黑体");
        headerFont.setFontHeightInPoints((short)15);
        headerFont.setBold(false);
        headerFont.setColor(IndexedColors.BLUE.index);
        headerStyle.setFont(headerFont);

        //设置表内容样式
        HSSFCellStyle contentStyle = hssfWorkbook.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setFillBackgroundColor(IndexedColors.BLUE.index);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);
        contentStyle.setBorderTop(BorderStyle.THIN);
        //设置内容字体
        Font contentFont = hssfWorkbook.createFont();
        contentFont.setFontName("黑体");
        contentFont.setFontHeightInPoints((short)15);
        contentFont.setBold(false);
        contentFont.setColor(IndexedColors.BLUE.index);
        contentStyle.setFont(headerFont);


        //标题行
        HSSFRow titleRow = hssfSheet.createRow(0);
        HSSFCell cell = titleRow.createCell(0);
        //标题行内容
        cell.setCellValue(title);
        //渲染标题行样式
        cell.setCellStyle(titleStyle);


        //表头
        HSSFRow headerRow = hssfSheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell hssfCell = headerRow.createCell(i);
            hssfCell.setCellValue(headers[i]);
            hssfCell.setCellStyle(headerStyle);
        }

        //创建内容
        for (int i = 0; i <values.length; i++){
            headerRow = hssfSheet.createRow(i +2);
            for (int j = 0; j < values[i].length; j++){
                //将内容按顺序赋给对应列对象
                HSSFCell hssfCell = headerRow.createCell(j);
                hssfCell.setCellValue(values[i][j]);
                hssfCell.setCellStyle(contentStyle);
            }
        }
        return hssfWorkbook;
    }

}

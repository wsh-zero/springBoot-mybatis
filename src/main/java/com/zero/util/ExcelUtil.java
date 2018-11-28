package com.zero.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.joda.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

public class ExcelUtil<T> {
    /**
     * https://www.cnblogs.com/dawnheaven/p/4462572.html
     * https://www.cnblogs.com/hanfeihanfei/p/7079210.html
     * https://www.cnblogs.com/zou90512/p/3989450.html
     */

    /**
     * @param response
     * @param
     */
    public void importExcel(HttpServletResponse response, String sheetName,
                            String[] headers, String[] contents, Collection<T> data,
                            String fileName, DateTimeFormatter formatter) {
        try (
                // 声明一个工作薄
                HSSFWorkbook workbook = new HSSFWorkbook();
                OutputStream output = response.getOutputStream()) {
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet(sheetName);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 15);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 生成一个字体
            HSSFFont font = workbook.createFont();
            //设置字体高度
            font.setFontHeightInPoints((short) 12);
            // 把字体应用到当前的样式
            style.setFont(font);
            style.setAlignment(HorizontalAlignment.CENTER);
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }

            // 遍历集合数据，产生数据行
            Iterator<T> it = data.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = it.next();
                for (short i = 0; i < contents.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style);
                    String getMethodName = contents[i];
                    Method getMethod = t.getClass().getMethod(getMethodName);
                    Object value = getMethod.invoke(t);
                    // 判断值的类型后进行强制类型转换
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    } else {
                        cell.setCellValue(value.toString());

                    }
                }

            }
            response.reset();
            //设置响应的编码
            response.setContentType("application/x-download");
            response.setCharacterEncoding("utf-8");
            //设置浏览器响应头对应的Content-disposition
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso8859-1") + ".xls");
            workbook.write(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

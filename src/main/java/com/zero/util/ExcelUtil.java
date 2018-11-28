package com.zero.util;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
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
     * @param response 请求
     * @param headers  表头
     * @param contents 内容get方法
     * @param data     数据
     * @param fileName 导出表名
     */
    public void exportExcel(HttpServletResponse response,
                            String[] headers, String[] contents, Collection<T> data,
                            String fileName) {
        if (null == headers) {
            throw new RuntimeException("获取表格头信息失败");
        }
        if (null != contents && headers.length != contents.length) {
            throw new RuntimeException(String.format("表格参数不一致 headers=%s contents=%s", headers.length, contents.length));
        }
        commonExcel(response, headers, contents, data, fileName);
    }

    /**
     * 通过实体类依次获取get方法
     *
     * @param response 请求
     * @param headers  表头
     * @param data     数据
     * @param fileName 导出表名
     */
    public void exportExcel(HttpServletResponse response, String[] headers, Collection<T> data, String fileName) {
        if (null == headers) {
            throw new RuntimeException("获取表格头信息失败");
        }
        commonExcel(response, headers, null, data, fileName);
    }

    public void commonExcel(HttpServletResponse response, String[] headers, String[] contents, Collection<T> data, String fileName) {
        try (
                // 声明一个工作薄
                HSSFWorkbook workbook = new HSSFWorkbook();
                OutputStream output = response.getOutputStream()) {
            // 生成一个表格(可以设置sheetName)
            HSSFSheet sheet = workbook.createSheet();
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 15);
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            // 把字体应用到当前的样式
            style.setFont(font);
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(headers[i]);
            }

            // 遍历集合数据，产生数据行
            if (null != data) {
                Iterator<T> it = data.iterator();
                int index = 0;
                while (it.hasNext()) {
                    index++;
                    row = sheet.createRow(index);
                    T t = it.next();
                    if (null != contents && contents.length > 0) {
                        for (short i = 0; i < contents.length; i++) {
                            HSSFCell cell = row.createCell(i);
                            String getMethodName = contents[i];
                            Method getMethod = t.getClass().getMethod(getMethodName);
                            Object value = getMethod.invoke(t);
                            // 判断值的类型后进行强制类型转换
                            typeConversion(cell, value);
                        }
                    } else {
                        // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                        Field[] fields = t.getClass().getDeclaredFields();
                        if (headers.length != fields.length) {
                            throw new RuntimeException(String.format("表格参数不一致 headers=%s contents=%s", headers.length, fields.length));
                        }
                        for (short i = 0; i < fields.length; i++) {
                            HSSFCell cell = row.createCell(i);
                            Field field = fields[i];
                            String fieldName = field.getName();
                            String getMethodName = "get"
                                    + fieldName.substring(0, 1).toUpperCase()
                                    + fieldName.substring(1);
                            Method getMethod = t.getClass().getMethod(getMethodName);
                            Object value = getMethod.invoke(t);
                            // 判断值的类型后进行强制类型转换
                            typeConversion(cell, value);
                        }
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

    private void typeConversion(HSSFCell cell, Object value) {
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue(value.toString());

        }
    }
}

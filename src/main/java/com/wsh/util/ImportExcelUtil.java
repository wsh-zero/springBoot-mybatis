package com.wsh.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ImportExcelUtil {
    /**
     * 排除第一行
     *
     * @param file 上传的文件
     * @return 导入数据
     */
    public static List<List<String>> importExcel(MultipartFile file) {
        return importExcel(file, 1);
    }

    /**
     * @param file       上传的文件
     * @param excludeRow 排除行数(从上排除)
     * @return 导入数据
     */
    public static List<List<String>> importExcel(MultipartFile file, int excludeRow) {
        if (file == null) {
            throw new RuntimeException("获取文件信息失败");
        }
        try (InputStream is = file.getInputStream();
             XSSFWorkbook wb = new XSSFWorkbook(is)) {
            if (file.getSize() > 5 * 1024 * 1024) {//限制到5M
                throw new RuntimeException("文件太大，上传失败");
            }
            List<List<String>> dataLst = new ArrayList<>();

            //获取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            //获取excel的行数
            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCells = 0;
            if (totalRows >= 1 && null != sheet.getRow(0)) {
                totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            }

            //循环excel的行
            int r = excludeRow;
            for (; r < totalRows; r++) {
                Row row = sheet.getRow(r);
                if (null == row) {
                    continue;
                }
                //循环excel的列
                List<String> rowLst = new ArrayList<>();
                for (int c = 0; c < totalCells; c++) {
                    Cell cell = row.getCell(c);
                    if (null == cell) {
                        continue;
                    }
                    String cellValue = getCellValue(cell);
                    rowLst.add(cellValue);
                }
                //保存第r行的数据
                dataLst.add(rowLst);
            }

            return dataLst;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param cell excel单元
     * @return String
     * @throws
     * @description: 根据excel单元数据类型获取内容
     * @author yuanshi.fu
     * @date 2018/5/23 下午2:27
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (null != cell) {
            // 以下是判断数据的类型
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case _NONE:
                    break;
                case BOOLEAN:
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case NUMERIC:
                    //处理double类型的  1.0===》1
                    DecimalFormat df = new DecimalFormat("0");
                    String s = df.format(cell.getNumericCellValue());
                    cellValue = s;
                    break;
                default:
                    cellValue = cell.toString();
            }

        }
        return cellValue;
    }
}

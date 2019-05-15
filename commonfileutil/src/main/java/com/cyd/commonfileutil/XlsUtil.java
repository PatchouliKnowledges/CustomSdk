package com.cyd.commonfileutil;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Execel文件的生成读取的操作类
 * notice: 此处生成的文件为2003版本的xls格式，不支持2007版本的xlsx
 * 参考： poi的api： http://poi.apache.org/apidocs/4.1/    poi的sample：https://poi.apache.org/components/spreadsheet/quick-guide.html
 */
public class XlsUtil {


    /**
     * 创建一个xls文件，并新创建一个sheet表
     * notice:创建xls，必需也创建一个默认sheet表，不然创建之后会打开失败
     *
     * @param filePath  xls文件的路径
     * @param tableName xls文件的第一个sheet表的名字
     * @return true 生成成功，false 失败
     */
    public boolean createXlsFile(String filePath, String tableName) {
        File mFile = new File(filePath);
        try {
            FileUitl.isFile(filePath);
            Workbook wb = new HSSFWorkbook();
            wb.createSheet(tableName); //在execel里新建一个名为“tableName”的表
            FileOutputStream mFout = new FileOutputStream(mFile);
            wb.write(mFout);
            mFout.flush();
            mFout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 在新表或者旧表里面插入一行新数据(原数据保留)
     * notic：此处使用的execel的文件格式为2003版本，结尾为xls，不支持2007版本的解析
     *
     * @param filePath  xls文件的路径
     * @param tableName 需要插入数据的表
     * @param data      需要插入的一行数据
     * @return true 成功，false 失败
     */
    public boolean saveFileToExecels(String filePath, String tableName, String[] data) {
        try {
            FileUitl.isFile(filePath);
            FileInputStream mInput = new FileInputStream(filePath);
            Workbook wb = new HSSFWorkbook(mInput);
            Sheet sheet = wb.getSheet(tableName);
            if (sheet == null)
                throw new FileNotFoundException("The name of sheet is not found ,please check the name");
            int postion = sheet.getLastRowNum();
            postion = postion < 0 ? 0 : postion + 1;
            // 填一行内容
            Row row = sheet.createRow(postion);
            for (int i = 0; i < data.length; i++) {
                row.createCell(i).setCellValue(data[i]);
            }
            FileOutputStream mOut = new FileOutputStream(filePath);
            wb.write(mOut);
            mInput.close();
            mOut.flush();
            mOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}

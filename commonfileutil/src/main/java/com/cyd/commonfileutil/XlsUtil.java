package com.cyd.commonfileutil;


import android.text.TextUtils;

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

    //xls文件具体路径
    private String filePath;
    //xls的sheet表名
    private String tableName;

    /**
     * 初始化赋值操作
     *
     * @param filePath  详见：{@link #filePath}
     * @param tableName 详见：{@link #tableName}
     */
    public void init(String filePath, String tableName) {
        this.filePath = filePath;
        this.tableName = tableName;
    }

    /**
     * @return 返回当前文件路径
     * @see #filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return 返回当前sheet表名
     * @see #tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 判断filePath和tableName是否为空
     */
    private void ifEmpty_filePath_tableName() {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(tableName)) {
            throw new NullPointerException("filePath and tableName all can't be null ");
        }
    }

    /**
     * 创建一个xls文件，并新创建一个sheet表
     * notice : 使用此方法需保证 {@link #filePath} {@link #tableName}有初始化
     *
     * @see #createXlsFile(String, String)
     */
    public boolean createXlsFile() {
        ifEmpty_filePath_tableName();
        return createXlsFile(filePath, tableName);
    }

    /**
     * 创建一个xls文件，并新创建一个sheet表
     * notice:创建xls，必需也创建一个默认sheet表，不然创建之后会打开失败
     *
     * @param filePath  xls文件的路径
     * @param tableName xls文件的第一个sheet表的名字
     */
    public boolean createXlsFile(String filePath, String tableName) {
        File mFile = new File(filePath);
        try {
            Workbook wb = new HSSFWorkbook();
            wb.createSheet(tableName); //在execel里新建一个名为“tableName”的表
            FileOutputStream mFout = new FileOutputStream(mFile);
            wb.write(mFout);
            mFout.flush();
            mFout.close();
        } catch (IOException e) {
            MLog.e("error = " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 在已有的表里面插入一行新数据，若没数据则从第一行开始(原数据保留)
     * notice : 使用此方法需保证 {@link #filePath} {@link #tableName}不为空
     *
     * @param data 需要新插入的一行数据
     * @return true 成功，false 失败
     */
    public boolean saveFileToExecels(String[] data) {
        return saveFileToExecels(filePath, tableName, data);
    }

    /**
     * 在已有的表里面插入一行新数据，若没数据则从第一行开始(原数据保留)
     * notic：此处使用的execel的文件格式为2003版本，结尾为xls，不支持2007版本的解析
     *
     * @param filePath  xls文件的路径
     * @param tableName 需要插入数据的表
     * @param data      需要新插入的一行数据
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
            postion = postion == 0 ? 0 : postion + 1;
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
            return false;
        }
        return true;
    }

}

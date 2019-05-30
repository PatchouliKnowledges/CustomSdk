package com.cyd.commonfileutil;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作类，用于创建文件
 */
public final class FileUitl {

    /**
     * 创建文件夹
     *
     * @param path 文件路径
     * @return true成功，false失败
     */
    public static boolean createDirectory(String path) {
        return createFileOrDirectory(path, true);
    }

    /**
     * 创建文件夹或者文件
     *
     * @param path 文件夹或者文件路径
     * @param flag ture创建文件夹，false创建文件(notic:可以创建txt文件，但无法创建xls等特殊格式)
     * @return true成功或者文件已存在，false失败
     */
    public static boolean createFileOrDirectory(String path, boolean flag) {
        try {
            File mFile = new File(path);
            if (!mFile.exists()) {
                return flag ? mFile.mkdirs() : mFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断当前文件是否为存在或者是否为文件
     *
     * @param filePath 文件路径
     * @return true 是文件，false 不是文件或者不存在此文件
     */
    public static boolean isFile(String filePath) throws FileNotFoundException {
        File mFile = new File(filePath);
        if (!mFile.exists()) {
            throw new FileNotFoundException("The file is not exists");
        } else if (!mFile.isFile()) {
            throw new FileNotFoundException("The filePath is not a file , error: filePath = " + filePath);
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return true 删除成功，false 失败
     */
    public static boolean deleteFile(String filePath) {
        try {
            isFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File mFile = new File(filePath);
        return mFile.exists() && mFile.delete();
    }

    /**
     * 写数据到指定路径的文件
     *
     * @param filePath  文件路径
     * @param writeData 要写入的数据
     * @return true 成功，false 失败
     */
    public static boolean writeFile(String filePath, String writeData) {
        return writeFile(filePath, writeData);
    }

    /**
     * 写数据到指定路径的文件
     *
     * @param filePath  文件路径
     * @param writeData 要写入的数据
     * @return true 成功，false 失败
     */
    public static boolean writeFile(String filePath, byte[] writeData) {
        try {
            FileOutputStream fOut = new FileOutputStream(filePath);
            fOut.write(writeData);
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读文件内容
     *
     * @param filePath 文件路径
     * @return null失败，其他为文件内容
     */
    public static String readFile(String filePath) {
        try {
            if (isFile(filePath)) {
                FileInputStream fInput = new FileInputStream(filePath);
                byte[] tempData = new byte[fInput.available()];
                fInput.read(tempData);
                fInput.close();
                return new String(tempData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

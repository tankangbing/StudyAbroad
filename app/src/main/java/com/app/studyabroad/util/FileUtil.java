package com.app.studyabroad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    @SuppressWarnings("finally")
    public static String readFileByChars(File file) {
        StringBuffer rtstr = new StringBuffer();
        if(null == file) return "";
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                rtstr.append(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            return rtstr.toString();
        }
    }

}

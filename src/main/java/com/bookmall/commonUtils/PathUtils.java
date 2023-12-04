package com.bookmall.commonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 路径工具类
 *  用来实现存储文件的路径获取， 通过调用System
 */
public class PathUtils {
    public static String getClassLoadRootPath() {
        String path = "";
        try {
            // 对图片进行编码
            String prePath = URLDecoder.decode(PathUtils.class.getClassLoader().getResource("").getPath(),"utf-8").replace("/target/classes", "");
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("mac")) {
                // 苹果
                path = prePath.substring(0, prePath.length() - 1);
            } else if (osName.toLowerCase().startsWith("windows")) {
                // windows
                path = prePath.substring(1, prePath.length() - 1);
            } else if(osName.toLowerCase().startsWith("linux") || osName.toLowerCase().startsWith("unix")) {
                // unix or linux
                path = prePath.substring(0, prePath.length() - 1);
            } else {
                path = prePath.substring(1, prePath.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }

//    public static void main(String[] args) {
//        String classLoadRootPath = getClassLoadRootPath();
//        System.out.println("class: " + classLoadRootPath);
//    }
}

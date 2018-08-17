package cn.devinkin.crm.utils;

import java.util.UUID;

public class UploadUtils {

    /**
     * 传入文件的名称，返回的是唯一的名称
     * 例如：girl.jpg -> 返回alskdjflasjkdf.jpg
     * @param filename 文件名称
     * @return uuid后的文件名称
     */
    public static String getUUIDName(String filename) {
        // 先查找
        int index = filename.lastIndexOf(".");
        // 截取
        filename = filename.substring(index, filename.length());

        // 唯一的字符串
        String uuid = UUID.randomUUID().toString().replace("-","");
        filename = uuid + filename;
        System.out.println(filename);
        return filename;
    }

    public static void main(String[] args) {
        String filename = "girl.jpg";
        getUUIDName(filename);
    }
}

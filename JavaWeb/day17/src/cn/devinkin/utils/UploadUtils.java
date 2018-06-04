package cn.devinkin.utils;

import java.util.UUID;

public class UploadUtils {
    /**
     * 获取随机名称
     * @param realName 文件真实名称
     * @return uuid
     */
    public static String getUUIDName(String realName) {
        //realName可能有后缀名，也有可能没有
        int index = realName.lastIndexOf(".");
        if (index == -1) {
            return UUID.randomUUID().toString().replace("-","").toUpperCase();
        } else {
            return UUID.randomUUID().toString().replace("-","").toUpperCase() + realName.substring(index);
        }
    }

    /**
     * 获取文件真实名称
     * @param name 传入文件的名称
     * @return 文件名称
     */
    public static String getRealName(String name) {
        int index = name.lastIndexOf("/");
        return name.substring(index + 1);
    }


    /**
     * 获取存放文件的目录，使用目录打散的机制
     * @param name 文件名称
     * @return 存放上传文件的目录
     */
    public static String getDir(String name) {
        int i = name.hashCode();
        String hex = Integer.toHexString(i);
        int j = hex.length();
        for (int k = 0; k < 8-j; k++) {
            hex = "0" + hex;
        }
        return "/" + hex.charAt(0) + "/" + hex.charAt(1);
    }

//    public static void main(String[] args) {
//        String s = "0";
//        String s1 = "1.jpg";
//        System.out.println(getUUIDName(s));
//        System.out.println(getUUIDName(s1));
//        System.out.println(getRealName(s));
//        System.out.println(getRealName(s1));
//        System.out.println(getDir(s));
//        System.out.println(getDir(s1));
//    }
}

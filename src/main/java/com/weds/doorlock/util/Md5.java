package com.weds.doorlock.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    /* public static String getMD5EncodeBase64(String str) {
       try {
           MessageDigest digest;
           digest = MessageDigest.getInstance("md5");
           byte[] result = digest.digest(str.getBytes());
           return Base64.getEncoder().encodeToString(result);
       } catch (Exception e) {
           throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
       }
   }*/
    //32
    public static String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }

    public static String MD5_16(String sourceStr) {
        String result = "";//通过result返回结果值
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//1.初始化MessageDigest信息摘要对象,并指定为MD5不分大小写都可以
            md.update(sourceStr.getBytes());//2.传入需要计算的字符串更新摘要信息，传入的为字节数组byte[],将字符串转换为字节数组使用getBytes()方法完成
            byte b[] = md.digest();//3.计算信息摘要digest()方法,返回值为字节数组

            int i;//定义整型
            //声明StringBuffer对象
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];//将首个元素赋值给i
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");//前面补0
                buf.append(Integer.toHexString(i));//转换成16进制编码
            }
            result = buf.toString();//转换成字符串
            //System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));//输出16位16进制字符串

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;//返回结果
    }
}

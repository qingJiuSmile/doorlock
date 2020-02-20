package com.weds.doorlock.util;
 
import java.net.*;
 
public class Ipconfig {
 
    public static void main(String[] args) throws Exception {
        System.out.println( "mac地址："+getMACAddress());
    }


    //获取MAC地址的方法  
    public static String getMACAddress()throws Exception{
        InetAddress ia;
        try {
            ia= InetAddress.getLocalHost();

            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
            System.out.println("本机名称是："+ localname);
            System.out.println("本机的ip是 ："+localip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InetAddress ia1 = InetAddress.getLocalHost();//获取本地IP对象
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。  
        byte[] mac = NetworkInterface.getByInetAddress(ia1).getHardwareAddress();
          
        //下面代码是把mac地址拼装成String  
        StringBuffer sb = new StringBuffer();  
          
        for(int i=0;i<mac.length;i++){  
            if(i!=0){  
                sb.append("-");  
            }  
            //mac[i] & 0xFF 是为了把byte转化为正整数  
            String s = Integer.toHexString(mac[i] & 0xFF);  
            sb.append(s.length()==1?0+s:s);
        }  
          
        //把字符串所有小写字母改为大写成为正规的mac地址并返回  
        return sb.toString().toUpperCase();  
    }  
 
}
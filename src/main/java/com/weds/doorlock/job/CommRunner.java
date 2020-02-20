package com.weds.doorlock.job;


import com.weds.doorlock.entity.CodeEntity;
import com.weds.doorlock.service.OpenTheDoorService;
import com.weds.doorlock.util.Ipconfig;
import com.weds.doorlock.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 开启服务启动的方法
 */
@Component
public class CommRunner implements ApplicationRunner {


    /**
     *   所需加密的字段
     */
    private final String secretKey = "weds123456";

    /**
     * 所需判断加密的字段
     */
    @Value("${doorLock.lock-key}")
    private String lockKey;

    @Autowired
    private OpenTheDoorService openTheDoorService;

    /** 
    * @Description: 程序启动时判断后台Lock-key是否与配置文件一致，若不一致则停止运行 
    * @Param: [applicationArguments] 
    * @return: void 
    * @Author: tjy
    * @Date: 2020/2/18
    */ 
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.err.println("初始化 判断密钥。。。");
        //获得本机 MAC 地址
        String mac = Ipconfig.getMACAddress();
        //本机 MAC 地址 + secretKey 的MD5值，十六位大写
        String returnKey = Md5.getMD5(mac + this.secretKey).toUpperCase().substring(8, 24);
        StringBuffer sb = new StringBuffer();
        String[] macArr = mac.split("-");
        for (String str:macArr){
            sb.append(str);
        }
        if(!this.lockKey.equals(returnKey)){
            throw new Exception(sb.toString()+" 密钥验证错误！");
        }
        //主动调用门锁的获取门锁地址接口（GetManagedLocks），将获取到的数据写入到scm的engin_lock
         CodeEntity code = openTheDoorService.getManagedLocks();
         System.err.println("初始化写入engin_lock结果 : ============ \n"+ code.getMsg());
    }
}

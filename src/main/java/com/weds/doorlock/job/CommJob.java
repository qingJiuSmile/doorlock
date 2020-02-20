package com.weds.doorlock.job;

import com.weds.doorlock.entity.CodeEntity;
import com.weds.doorlock.service.OpenTheDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CommJob {

    @Autowired
    private OpenTheDoorService openTheDoorService;

    @Scheduled(cron="0 0 20 * * ?")
    public void  addLockLs(){
        //主动调用门锁的获取门锁地址接口（GetManagedLocks），将获取到的数据写入到scm的engin_lock
        CodeEntity code = openTheDoorService.getManagedLocks();
        System.err.println("定时任务写入 engin_lock 结果 : ============ \n" + code.getMsg());
    }

}

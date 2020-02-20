package com.weds.doorlock.web;

import com.weds.doorlock.entity.CodeEntity;
import com.weds.doorlock.service.OpenTheDoorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/remoteOpenLock")
@Api(description = "门锁接口")
public class OpenTheDoorController {

    @Autowired
    private OpenTheDoorService open;


    @GetMapping("/openLock")
    @ApiOperation(value = "远程遥控开门")
    public CodeEntity openLock(@RequestParam String lockAddr, @RequestParam Integer openMode,@RequestParam String user,@RequestParam String pass){
      return  open.openLock(lockAddr,openMode,user,pass);
    }


    @PostMapping("/getManagedLocks")
    @ApiOperation(value = "获取门锁地址列表")
    public CodeEntity getManagedLocks(){
       return open.getManagedLocks();
    }
}

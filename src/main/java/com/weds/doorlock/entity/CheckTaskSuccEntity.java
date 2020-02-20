package com.weds.doorlock.entity;

import lombok.Data;

@Data
public class CheckTaskSuccEntity {

    //请求的名字
    private String request;
    //请求中的pkgId
    private Integer pkgId;
    //请求是否正确，0=正确，>0表示不同类型的错误码。参照附表一
    private Integer result;
    //当返回的result不等于0时，有效，返回的错误信息的描述
    private String error;
    //任务的ID号。后续app可以根据这个ID，调用CheckTaskSucc接口
    private Integer taskId;
    //true表示异步任务成功，false表示失败
    private boolean taskSucc;


}

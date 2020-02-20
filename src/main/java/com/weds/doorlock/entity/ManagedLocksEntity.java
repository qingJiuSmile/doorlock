package com.weds.doorlock.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
* @Description: 门锁地址列表实体
* @Param:
* @return:
* @Author: tjy
* @Date: 2020/2/18
*/
@Data
@XStreamAlias("eglockcloud")
public class ManagedLocksEntity {

    @XStreamAlias("request")
    private String request;

    @XStreamAlias("pkgId")
    private Integer pkgId;

    @XStreamAlias("result")
    private Integer result;

    @XStreamAlias("lockAddrs")
    private AddrEntity lockAddrs;

}

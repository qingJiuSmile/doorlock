package com.weds.doorlock.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;

@Data
@XStreamAlias("addr")
@XStreamConverter(AddrConverter.class)
public class LockAddrEntity {

    private String addr;

    private String location;
}

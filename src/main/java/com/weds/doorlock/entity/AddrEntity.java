package com.weds.doorlock.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XStreamAlias("lockAddrs")
public class AddrEntity {

    @XStreamImplicit(itemFieldName = "addr")
    private List<LockAddrEntity> list = new ArrayList<>();
}

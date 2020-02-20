package com.weds.doorlock.entity;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AddrConverter implements Converter {

    /**
     * 将java对象转为xml时使用
     */
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        LockAddrEntity attr = (LockAddrEntity) o;
        hierarchicalStreamWriter.addAttribute("location", attr.getLocation());
        hierarchicalStreamWriter.setValue(attr.getAddr());
    }


    /**
     * 将xml转为java对象使用
     */

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        // 在解析attribute元素时，先创建一个CarAttr对象
        LockAddrEntity a = new LockAddrEntity();
        // 将attribute元素的name属性设置为CarAttr对象的name属性值
        a.setLocation(hierarchicalStreamReader.getAttribute("location"));
        // 将attribute元素的txt值设置为CarAttr对象的value值
        a.setAddr(hierarchicalStreamReader.getValue());
        return a;
    }

    @Override
    public boolean canConvert(Class aClass) {
        //转换条件
        return aClass.equals(LockAddrEntity.class);
    }
}

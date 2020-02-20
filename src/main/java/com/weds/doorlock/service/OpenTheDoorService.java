package com.weds.doorlock.service;

import com.thoughtworks.xstream.XStream;
import com.weds.doorlock.entity.CheckTaskSuccEntity;
import com.weds.doorlock.entity.CodeEntity;
import com.weds.doorlock.entity.EnginLockUser;
import com.weds.doorlock.entity.ManagedLocksEntity;
import com.weds.doorlock.mapper.EnginLockMapper;
import com.weds.doorlock.mapper.EnginLockUserMapper;
import com.weds.doorlock.util.Md5;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenTheDoorService {


    @Value("${doorLock.lock-webservice}")
    private String url;

    @Value("${doorLock.lock-pkgId}")
    private Integer pkgId;

    @Autowired
    private EnginLockMapper enginLockMapper;

    @Autowired
    private EnginLockUserMapper enginLockUserMapper;

    /**
    * @Description: 检查开门指令是否成功
    * @Param: []
    * @return: com.weds.doorlock.entity.CheckTaskSuccEntity
    * @Author: tjy
    * @Date: 2020/2/17
    */
    public CheckTaskSuccEntity checkTaskSucc(Integer taskId,String user,String pass){
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        // 需要密码的情况需要加上用户名和密码
        //client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects ;
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            String pwd = Md5.getMD5(pass);
            String parm = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<eglockcloud name=\"CheckTaskSucc\">\n" +
                    "\t<pkgId>"+ this.pkgId +"</pkgId>\n" +
                    "\t<user>"+ user +"</user>\n" +
                    "\t<pass>" + pwd + "</pass>\n" +
                    "\t<taskId>"+ taskId +"</taskId>\n" +
                    "</eglockcloud>";
            objects = client.invoke("transXml",parm);
            //创建xStream对象
            XStream xstream = new XStream();
            //将别名与xml名字相对应
            xstream.alias("eglockcloud", CheckTaskSuccEntity.class);
            CheckTaskSuccEntity user2 = (CheckTaskSuccEntity) xstream.fromXML(objects[0].toString());
            return user2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
    * @Description: 远程遥控开门
    * @Param: [lockAddr, openMode]
    * @return: com.weds.doorlock.entity.CheckTaskSuccEntity
    * @Author: tjy
    * @Date: 2020/2/18
    */
    public CodeEntity openLock(String lockAddr,Integer openMode,String user,String pass){
        CodeEntity code = new CodeEntity();
        if(StringUtils.isBlank(lockAddr) || openMode == null || StringUtils.isBlank(user) || StringUtils.isBlank(pass)){
            code.setCode(601);
            code.setMsg("参数为空！");
            return code;
        }
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(this.url);
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            String pwd = Md5.getMD5(pass);
            String idNo = "idNo_" + user;
            String parm = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<eglockcloud name=\"RemoteOpenLock\">\n" +
                    "\t<pkgId>"+ this.pkgId +"</pkgId>\n" +
                    "\t<user>"+ user +"</user>\n" +
                    "\t<pass>"+ pwd +"</pass>\n" +
                    "\t<lockAddr>"+ lockAddr +"</lockAddr>\n" +
                    "\t<openMode>"+ openMode +"</openMode>\n" +
                    "\t<customInfo>" +idNo+ "</customInfo>\n" +
                    "</eglockcloud>";
            objects = client.invoke("transXml",parm);
            //创建xStream对象
            XStream xstream = new XStream();
            //将别名与xml名字相对应
            xstream.alias("eglockcloud", CheckTaskSuccEntity.class);
            CheckTaskSuccEntity user2 = (CheckTaskSuccEntity) xstream.fromXML(objects[0].toString());
            System.out.println(user2);
            //判断返回的result是否等于0
            if(user2 == null ||user2.getResult() != 0 || checkTaskSucc(user2.getTaskId(),user,pass).getResult() != 0){
                code.setCode(601);
                code.setMsg("开门失败！");
                return code;
            }
            code.setMsg("成功");
            code.setCode(600);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * @Description: 门锁地址列表
    * @Param: []
    * @return: void
    * @Author: tjy
    * @Date: 2020/2/18
    */
    public CodeEntity getManagedLocks(){
        CodeEntity code = new CodeEntity();
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(this.url);
        Object[] objects = new Object[0];
        try {
            //从库里查询出所有用户账号
            List<EnginLockUser> ls = enginLockUserMapper.getLockUserAll();
            StringBuffer succData = new StringBuffer();
            StringBuffer faildData = new StringBuffer();
            if(ls != null){
                //过滤掉缺少账号/密码的信息
                List<EnginLockUser> lsData = ls.stream()
                        .filter((e) -> StringUtils.isNotBlank(e.getPw()) && StringUtils.isNotBlank(e.getZh()))
                        .collect(Collectors.toList());
                //循环获得对应账号所控制的门锁
                for (EnginLockUser data: lsData){
                    // invoke("方法名",参数1,参数2,参数3....);
                    String pwd = Md5.getMD5(data.getPw());
                    String parm = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<eglockcloud name=\"GetManagedLocks\">\n" +
                            "\t<pkgId>"+ this.pkgId +"</pkgId>\n" +
                            "\t<user>"+ data.getZh() +"</user>\n" +
                            "\t<pass>"+ pwd +"</pass>\n" +
                            "</eglockcloud>";
                    objects = client.invoke("transXml",parm);
                    System.out.println(objects[0]);
                    //创建xStream对象
                    XStream xstream = new XStream();
                    //将别名与xml名字相对应
                    xstream.autodetectAnnotations(true);
                    xstream.processAnnotations(ManagedLocksEntity.class);
                    ManagedLocksEntity user2 = (ManagedLocksEntity) xstream.fromXML(objects[0].toString());
                    //这里判断请求的门锁列表是否有值；如果没值则不进行查询，并记录失败。
                    if(user2 == null ||user2.getResult() != 0 || user2.getLockAddrs().getList() == null){
                        faildData.append(data.getZh() + ",");
                    }else {
                        enginLockMapper.addDoorLockLs(user2.getLockAddrs().getList(),data.getZh());
                        succData.append(data.getZh() + ",");
                    }
                }
                code.setMsg("成功账号:" + succData.toString() +"\n" + "失败账号:" + faildData.toString());
                code.setCode(600);
                return code;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

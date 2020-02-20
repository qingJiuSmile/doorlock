package com.weds.doorlock.mapper;

import com.weds.doorlock.entity.EnginLock;
import com.weds.doorlock.entity.LockAddrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EnginLockMapper {
    int deleteByPrimaryKey(Integer xh);

    int insert(EnginLock record);

    int insertSelective(EnginLock record);

    EnginLock selectByPrimaryKey(Integer xh);

    int updateByPrimaryKeySelective(EnginLock record);

    int updateByPrimaryKey(EnginLock record);

    int addDoorLockLs(@Param("ls") List<LockAddrEntity> ls,@Param("zh") String zh);
}
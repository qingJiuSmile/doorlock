package com.weds.doorlock.mapper;

import com.weds.doorlock.entity.EnginLockUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EnginLockUserMapper {
    int deleteByPrimaryKey(Integer xh);

    int insert(EnginLockUser record);

    int insertSelective(EnginLockUser record);

    EnginLockUser selectByPrimaryKey(Integer xh);

    int updateByPrimaryKeySelective(EnginLockUser record);

    int updateByPrimaryKey(EnginLockUser record);

    List<EnginLockUser> getLockUserAll();
}
package com.campus.talent.mapper;

import com.campus.talent.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserMapper {

    @Select("SELECT id, username, password, member_id AS memberId, role FROM sys_user WHERE username = #{username}")
    SysUser findByUsername(String username);
}
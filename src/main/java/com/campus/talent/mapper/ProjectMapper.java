package com.campus.talent.mapper;

import com.campus.talent.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectMapper {

    // 查询所有项目
    List<Project> findAll();

    // 根据ID查询项目
    Project findById(@Param("id") Long id);

    // 查询某成员参与的所有项目
    List<Project> findByMemberId(@Param("memberId") Long memberId);

    // 新增项目
    int insert(Project project);

    // 更新项目
    int update(Project project);

    // 删除项目
    int deleteById(@Param("id") Long id);
}
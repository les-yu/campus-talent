package com.campus.talent.mapper;

import com.campus.talent.entity.SkillTag;
import com.campus.talent.entity.SkillCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkillMapper {

    // 查询所有技能分类
    List<SkillCategory> findAllCategories();

    // 查询所有技能标签（含分类名称）
    List<SkillTag> findAllTags();

    // 根据分类ID查询技能
    List<SkillTag> findTagsByCategoryId(@Param("categoryId") Integer categoryId);

    // 查询某成员的所有技能
    List<SkillTag> findByMemberId(@Param("memberId") Long memberId);
}
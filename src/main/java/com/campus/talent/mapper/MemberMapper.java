package com.campus.talent.mapper;

import com.campus.talent.dto.MemberSearchDTO;
import com.campus.talent.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 查询所有成员（含技能和项目）
    List<Member> findAll();

    // 根据ID查询单个成员（含技能和项目）
    Member findById(@Param("id") Long id);

    // 多条件组合查询
    List<Member> search(@Param("dto") MemberSearchDTO dto);

    // 新增成员
    int insert(Member member);

    // 更新成员信息
    int update(Member member);

    // 删除成员
    int deleteById(@Param("id") Long id);

    // 删除成员的所有技能关联
    int deleteSkillsByMemberId(@Param("memberId") Long memberId);

    // 批量插入成员技能关联
    int insertSkills(@Param("memberId") Long memberId,
                     @Param("skillIds") List<Long> skillIds);
}
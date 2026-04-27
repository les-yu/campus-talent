package com.campus.talent.service;

import com.campus.talent.dto.MemberSearchDTO;
import com.campus.talent.dto.PageResult;
import com.campus.talent.entity.Member;

import java.util.List;

public interface MemberService {

    // 分页搜索（替换原来的 findAll 和 search）
    PageResult<Member> searchPage(MemberSearchDTO dto);

    Member findById(Long id);

    void save(Member member, List<Long> skillIds);

    void update(Member member, List<Long> skillIds);

    void delete(Long id);
}
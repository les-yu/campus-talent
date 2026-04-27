package com.campus.talent.service.impl;

import com.campus.talent.dto.MemberSearchDTO;
import com.campus.talent.dto.PageResult;
import com.campus.talent.entity.Member;
import com.campus.talent.mapper.MemberMapper;
import com.campus.talent.service.MemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public PageResult<Member> searchPage(MemberSearchDTO dto) {
        // PageHelper 在查询前设置分页参数，自动拦截下一条 SQL
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Member> list = memberMapper.search(dto);
        return PageResult.of(new PageInfo<>(list));
    }

    @Override
    public Member findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    @Transactional
    public void save(Member member, List<Long> skillIds) {
        memberMapper.insert(member);
        if (skillIds != null && !skillIds.isEmpty()) {
            memberMapper.insertSkills(member.getId(), skillIds);
        }
    }

    @Override
    @Transactional
    public void update(Member member, List<Long> skillIds) {
        memberMapper.update(member);
        if (skillIds != null) {
            memberMapper.deleteSkillsByMemberId(member.getId());
            if (!skillIds.isEmpty()) {
                memberMapper.insertSkills(member.getId(), skillIds);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        memberMapper.deleteById(id);
    }
}
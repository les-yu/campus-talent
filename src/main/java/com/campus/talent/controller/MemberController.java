package com.campus.talent.controller;

import com.campus.talent.dto.MemberSearchDTO;
import com.campus.talent.dto.PageResult;
import com.campus.talent.dto.Result;
import com.campus.talent.entity.Member;
import com.campus.talent.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberService memberService;

    /** 分页搜索（合并了原来的 findAll 和 search） */
    @PostMapping("/search")
    public Result<PageResult<Member>> search(@RequestBody MemberSearchDTO dto) {
        return Result.success(memberService.searchPage(dto));
    }

    /** 根据ID获取成员详情 */
    @GetMapping("/{id}")
    public Result<Member> findById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        if (member == null) return Result.fail("成员不存在");
        return Result.success(member);
    }

    /** 新增成员 */
    @PostMapping
    public Result<Void> save(@RequestBody MemberSaveRequest req) {
        memberService.save(req.getMember(), req.getSkillIds());
        return Result.success();
    }

    /** 更新成员 */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id,
                               @RequestBody MemberSaveRequest req) {
        req.getMember().setId(id);
        memberService.update(req.getMember(), req.getSkillIds());
        return Result.success();
    }

    /** 删除成员 */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return Result.success();
    }

    @lombok.Data
    public static class MemberSaveRequest {
        private Member member;
        private List<Long> skillIds;
    }
}
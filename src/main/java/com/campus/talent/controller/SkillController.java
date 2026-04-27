package com.campus.talent.controller;

import com.campus.talent.dto.Result;
import com.campus.talent.entity.SkillCategory;
import com.campus.talent.entity.SkillTag;
import com.campus.talent.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
@CrossOrigin
public class SkillController {

    private final SkillService skillService;

    /** 获取所有技能分类 */
    @GetMapping("/categories")
    public Result<List<SkillCategory>> findAllCategories() {
        return Result.success(skillService.findAllCategories());
    }

    /** 获取所有技能标签 */
    @GetMapping
    public Result<List<SkillTag>> findAllTags() {
        return Result.success(skillService.findAllTags());
    }

    /** 按分类获取技能 */
    @GetMapping("/category/{categoryId}")
    public Result<List<SkillTag>> findByCategory(@PathVariable Integer categoryId) {
        return Result.success(skillService.findTagsByCategoryId(categoryId));
    }
}
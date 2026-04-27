package com.campus.talent.service.impl;

import com.campus.talent.entity.SkillCategory;
import com.campus.talent.entity.SkillTag;
import com.campus.talent.mapper.SkillMapper;
import com.campus.talent.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillMapper skillMapper;

    @Override
    public List<SkillCategory> findAllCategories() {
        return skillMapper.findAllCategories();
    }

    @Override
    public List<SkillTag> findAllTags() {
        return skillMapper.findAllTags();
    }

    @Override
    public List<SkillTag> findTagsByCategoryId(Integer categoryId) {
        return skillMapper.findTagsByCategoryId(categoryId);
    }
}
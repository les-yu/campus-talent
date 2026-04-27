package com.campus.talent.service;

import com.campus.talent.entity.SkillCategory;
import com.campus.talent.entity.SkillTag;

import java.util.List;

public interface SkillService {

    List<SkillCategory> findAllCategories();

    List<SkillTag> findAllTags();

    List<SkillTag> findTagsByCategoryId(Integer categoryId);
}
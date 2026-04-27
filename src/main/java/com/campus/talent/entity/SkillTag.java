package com.campus.talent.entity;

import lombok.Data;

@Data
public class SkillTag {
    private Long id;
    private Integer categoryId;
    private String name;
    private String iconUrl;

    // 关联字段
    private String categoryName;  // 查询时附带分类名称
}
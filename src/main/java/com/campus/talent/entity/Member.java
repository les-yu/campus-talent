package com.campus.talent.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Member {
    private Long id;
    private String name;
    private Integer gender;
    private String email;
    private String phone;
    private String school;
    private String major;
    private String grade;
    private String bio;
    private String avatarUrl;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 关联字段（查询时附带，不对应数据库列）
    private List<SkillTag> skills;
    private List<Project> projects;
}
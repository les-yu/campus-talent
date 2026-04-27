package com.campus.talent.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Project {
    private Long id;
    private String name;
    private String description;
    private String techStack;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private LocalDateTime createdAt;

    // 关联字段（成员在该项目中的角色，查询时附带）
    private String role;
    private String contribution;
}

package com.campus.talent.dto;

import lombok.Data;
import java.util.List;

@Data
public class MemberSearchDTO {
    private String name;
    private String school;
    private String major;
    private String grade;
    private List<Long> skillIds;
    private Long projectId;
    private Integer status;

    // 分页参数
    private Integer pageNum  = 1;   // 当前页，默认第1页
    private Integer pageSize = 6;   // 每页条数，默认6条

    public int getSkillCount() {
        return skillIds == null ? 0 : skillIds.size();
    }
}
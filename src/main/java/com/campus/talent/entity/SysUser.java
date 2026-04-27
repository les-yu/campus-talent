package com.campus.talent.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private Long memberId;
    private String role;
    private LocalDateTime createdAt;
}

package com.campus.talent.controller;

import com.campus.talent.dto.Result;
import com.campus.talent.mapper.SysUserMapper;
import com.campus.talent.util.JwtUtil;
import com.campus.talent.util.PasswordUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final SysUserMapper sysUserMapper;
    private final JwtUtil jwtUtil;
    private final PasswordUtil passwordUtil;

    /** 登录接口 */
    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody LoginRequest req) {
        var user = sysUserMapper.findByUsername(req.getUsername());
        if (user == null) {
            return Result.fail("用户名或密码错误");
        }
        // 明文比对密码
        if (!req.getPassword().equals(user.getPassword())) {
            return Result.fail("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        return Result.success(Map.of(
                "token", token,
                "username", user.getUsername(),
                "role", user.getRole()
        ));
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}

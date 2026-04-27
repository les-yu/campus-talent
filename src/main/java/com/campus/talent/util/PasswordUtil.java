package com.campus.talent.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /** 验证明文密码和 BCrypt 哈希是否匹配 */
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    /** 对明文密码进行 BCrypt 加密 */
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
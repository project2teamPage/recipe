package com.recipe.control;

import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import com.recipe.service.user.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    // 이메일 인증 서비스
    private final EmailService emailService;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/send_code")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String email){
        emailService.sendVerificationCode(email);
        return ResponseEntity.ok("인증 코드가 전송되었습니다.");

    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestParam String email,
                                                             @RequestParam String code) {
        boolean result = emailService.verifyCode(email, code);
        return ResponseEntity.ok(result ? "인증 성공" : "인증 실패");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String code,
                                           @RequestParam String newPassword) {
        if (!emailService.verifyCode(email, code)) {
            return ResponseEntity.badRequest().body("인증 실패");
        }
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return ResponseEntity.ok("비밀번호가 재설정되었습니다.");
    }




}

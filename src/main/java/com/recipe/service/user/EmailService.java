package com.recipe.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    // 이메일 인증
    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> emailCodeStore = new ConcurrentHashMap<>();

    public void sendVerificationCode(String email){
        String code = createRandomCode();
        emailCodeStore.put(email, code);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("인증 코드입니다.");
        simpleMailMessage.setText("인증 코드: " + code);
        mailSender.send(simpleMailMessage);
    }


    public boolean verifyCode(String email, String code) {
        return code.equals(emailCodeStore.get(email));
}

    private String createRandomCode() {
        return String.valueOf((int)(Math.random() * 899999) + 100000); // 6자리 숫자
    }

}

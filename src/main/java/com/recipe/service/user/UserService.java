package com.recipe.service.user;


import com.recipe.config.CustomUserDetails;
import com.recipe.dto.user.MainUserListDto;
import com.recipe.dto.user.MemberSignInDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.entity.user.User;
import com.recipe.repository.user.UserRepo;
import com.recipe.repository.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserRepository userRepository;


    // 회원가입 정보 저장
    public User saveUser(@Valid  MemberSignUpDto memberSignUpDto, PasswordEncoder passwordEncoder) {
        System.out.println(memberSignUpDto.getEmail());
        User user = memberSignUpDto.toUser(passwordEncoder);
        ValidUserId(user);

        return userRepo.save(user);

    }
    // 로그인 시큐리티 사용

    // 아이디 중복 체크
    private void ValidUserId(User user) {
        User find = userRepo.findByLoginId(user.getLoginId());

        // 찾은 아이디가 null값이 아니면(아이디 일치)
        if (find != null) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        //스프링 시큐리티 사용시 커스텀 로그인 DB의 데이터로 로그인진행하기 때문에 오버라이딩

        // 로그인 시 입력한 아이디로 회원 테이블에서 정보 조회
        User user = userRepo.findByLoginId(loginId);

        if( user == null){
            throw new UsernameNotFoundException(loginId);
        }

        return new CustomUserDetails(user);
    }

    // 로그인중인 User 객체 가져오기
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("로그인된 사용자가 없습니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();  // 여기서 진짜 User 리턴
        }

        throw new IllegalStateException("인증 객체가 CustomUserDetails 타입이 아닙니다.");
    }


    // 이미지 업로드

    public void updateProfile(Long userId, MultipartFile profileImage) throws Exception {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 이름 생성
            String fileName = UUID.randomUUID() + "_" + profileImage.getOriginalFilename();
            String uploadDir = "uploads/profile/";

            // 디렉토리 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 파일 저장
            File savedFile = new File(uploadDir + fileName);
            profileImage.transferTo(savedFile);

            // DB에 경로 저장
            user.setProfileImagePath("/" + uploadDir + fileName); // 예: /uploads/profile/uuid_filename.jpg
        }

        userRepo.save(user);
    }


    public Long getUserIdByPrincipal(Principal principal) {
// 로그인된 사용자의 아이디(loginId)를 가져옴
        String loginId = principal.getName();

        // loginId로 사용자 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return user.getId();
    }

    // 아이디 중복검사
    @Transactional(readOnly = true)
    public boolean checkLoginIdDuplication(String loginId) {
        return userRepo.existsByLoginId(loginId);
    }

    // 이메일 중복검사
    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        return userRepo.existsByEmail(email);
    }


    // 내 캘린더
    public User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public User getUserByLoginId(String loginId) {
        return userRepo.findByLoginId(loginId);
    }

    // 회원 탈퇴
    public boolean getOut(String email, String password){
        User user = userRepo.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("사용자 없음")
        );
        if(!passwordEncoder.matches(password, user.getPassword())){
            return false;
        }
        userRepo.delete(user);
        return true;
    }
}

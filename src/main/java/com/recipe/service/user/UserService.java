package com.recipe.service.user;


import com.recipe.config.CustomUserDetails;
import com.recipe.constant.UploadType;
import com.recipe.dto.user.MainUserListDto;
import com.recipe.dto.user.MemberSignInDto;
import com.recipe.dto.user.MemberSignUpDto;
import com.recipe.dto.user.UserContentDto;
import com.recipe.entity.post.Post;
import com.recipe.entity.post.PostComment;
import com.recipe.entity.recipe.Recipe;
import com.recipe.entity.recipe.RecipeComment;
import com.recipe.entity.user.User;
import com.recipe.repository.post.PostCommentRepo;
import com.recipe.repository.post.PostRepo;
import com.recipe.repository.recipe.RecipeCommentRepo;
import com.recipe.repository.recipe.RecipeRepo;
import com.recipe.repository.user.UserRepo;
import com.recipe.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private RecipeCommentRepo recipeCommentRepo;

    @Autowired
    private PostCommentRepo postCommentRepo;


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


    @Transactional
    public void banUser(Long userId, String reason) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        user.setBanned(true);
        user.setBanTime(LocalDateTime.now());

        // 필요하다면 ban 사유도 저장 가능 (현재 User 엔티티엔 없지만, Report 쪽에 따로 넣고 있겠죠?)
        userRepo.save(user);
    }

    public boolean isUserBanned(User user) {
        if (!user.isBanned()) return false;

        LocalDateTime banEnd = user.getBanTime().plusDays(3);
        if (LocalDateTime.now().isAfter(banEnd)) {
            user.setBanned(false); // 자동으로 정지 해제
            user.setBanTime(null);
            userRepo.save(user);
            return false;
        }

        return true;
    }


    // 작성한 글 목록 가져오기
    public Page<UserContentDto> getContentDto(Long id, Pageable pageable) {

        List<Recipe> recipes = recipeRepo.findAllByUserIdAndIsDeletedFalse(id);
        List<Post> posts = postRepo.findAllByUserIdAndIsDeletedFalse(id);

        List<UserContentDto> contentDtoList = new ArrayList<>();

        if(recipes != null){
            for(Recipe recipe : recipes){
                UserContentDto dto = UserContentDto.fromRecipe(recipe);
                contentDtoList.add(dto);
            }
        }
        if(posts != null){
            for(Post post : posts){
                UserContentDto dto = UserContentDto.fromPost(post);
                contentDtoList.add(dto);
            }
        }

        contentDtoList.sort((a, b) -> {
            LocalDateTime dateA = a.getUpdateDate() != null ? a.getUpdateDate() : a.getUploadDate();
            LocalDateTime dateB = b.getUpdateDate() != null ? b.getUpdateDate() : b.getUploadDate();

            if (dateA == null && dateB == null) return 0;
            if (dateA == null) return 1;
            if (dateB == null) return -1;

            return dateB.compareTo(dateA); // 최신순 (내림차순)
        });

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), contentDtoList.size());
        List<UserContentDto> pageContent = contentDtoList.subList(start, end);


        return new PageImpl<>(pageContent, pageable, contentDtoList.size());
    }

    // 작성한 댓글 목록 가져오기
    public Page<UserContentDto> getCommentContentDto(Long id, Pageable pageable){

        List<RecipeComment> recipes = recipeCommentRepo.findAllByUserId(id);
        List<PostComment> posts = postCommentRepo.findAllByUserId(id);

        List<UserContentDto> contentDtoList = new ArrayList<>();

        if(recipes != null){
            for(RecipeComment recipe : recipes){
                UserContentDto dto = UserContentDto.fromRecipeComment(recipe);
                contentDtoList.add(dto);
            }
        }
        if(posts != null){
            for(PostComment post : posts){
                UserContentDto dto = UserContentDto.fromPostComment(post);
                contentDtoList.add(dto);
            }
        }

        contentDtoList.sort((a, b) -> {
            LocalDateTime dateA = a.getUpdateDate() != null ? a.getUpdateDate() : a.getUploadDate();
            LocalDateTime dateB = b.getUpdateDate() != null ? b.getUpdateDate() : b.getUploadDate();

            if (dateA == null && dateB == null) return 0;
            if (dateA == null) return 1;
            if (dateB == null) return -1;

            return dateB.compareTo(dateA); // 최신순 (내림차순)
        });

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), contentDtoList.size());
        List<UserContentDto> pageContent = contentDtoList.subList(start, end);


        return new PageImpl<>(pageContent, pageable, contentDtoList.size());


    }





}


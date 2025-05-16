package com.recipe.service.admin;

import com.recipe.dto.admin.ReportListDto;
import com.recipe.dto.admin.ReportRequestDto;
import com.recipe.entity.admin.Report;
import com.recipe.entity.user.User;
import com.recipe.repository.admin.ReportRepository;
import com.recipe.repository.post.PostCommentRepo;
import com.recipe.repository.post.PostRepo;
import com.recipe.repository.recipe.RecipeCommentRepo;
import com.recipe.repository.recipe.RecipeRepo;
import com.recipe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final RecipeRepo recipeRepo;
    private final RecipeCommentRepo recipeCommentRepo;
    private final PostRepo postRepo;
    private final PostCommentRepo postCommentRepo;

    // 레포트 테이블에서 데이터 전부 가져오기 - 메서드
    public List<ReportListDto> getReports() {

        List<ReportListDto> reportListDtos = new ArrayList<>();
        // 레포지토리를 통해서 테이블의 데이터 가져오기
        List<Report> reportList = reportRepository.findAllByOrderByDateDesc();

        // 엔티티를 dto에 넘겨서 arraryList로 저장
        for(Report report : reportList) {

            String targetNickName = "";
            String targetLoginId = "";
            String targetUrl = "#";

            switch (report.getTargetType()) {
                case RECIPE_POST:
                    targetNickName = recipeRepo.findById(report.getTargetId())
                            .map(recipe -> recipe.getUser().getNickName())
                            .orElse("알 수 없음");

                    targetLoginId = recipeRepo.findById(report.getTargetId())
                            .map(recipe -> recipe.getUser().getLoginId())
                            .orElse("알 수 없음");
                    targetUrl = "/recipe/" + report.getTargetId();
                    break;

                case RECIPE_COMMENT:
                    targetNickName = recipeCommentRepo.findById(report.getTargetId())
                            .map(comment -> comment.getUser().getNickName())
                            .orElse("알 수 없음");

                    targetLoginId = recipeCommentRepo.findById(report.getTargetId())
                            .map(comment -> comment.getUser().getLoginId())
                            .orElse("알 수 없음");
                    Long recipeId = recipeCommentRepo.findById(report.getTargetId())
                            .map(comment -> comment.getRecipe().getId())
                            .orElse(null);
                    if (recipeId != null) {
                        targetUrl = "/recipe/" + recipeId + "#comment-" + report.getTargetId(); // ✅ 댓글 앵커 포함
                    }
                    break;

                case COMMUNITY_POST:
                    targetNickName = postRepo.findById(report.getTargetId())
                            .map(post -> post.getUser().getNickName())
                            .orElse("알 수 없음");

                    targetLoginId = postRepo.findById(report.getTargetId())
                            .map(post -> post.getUser().getLoginId())
                            .orElse("알 수 없음");
                    targetUrl = "/community/" + report.getTargetId();
                    break;

                case COMMUNITY_COMMENT:
                    targetNickName = postCommentRepo.findById(report.getTargetId())
                            .map(comment -> comment.getUser().getNickName())
                            .orElse("알 수 없음");

                    targetLoginId = postCommentRepo.findById(report.getTargetId())
                            .map(comment -> comment.getUser().getLoginId())
                            .orElse("알 수 없음");
                    Long postId = postCommentRepo.findById(report.getTargetId())
                            .map(comment -> comment.getPost().getId())
                            .orElse(null);
                    if (postId != null) {
                        targetUrl = "/community/" + postId + "#comment-" + report.getTargetId(); // ✅ 댓글 앵커 포함
                    }
                    break;
            }

            reportListDtos.add(ReportListDto.from(report, targetLoginId, targetNickName, targetUrl));
        }

        return reportListDtos;
    }

    public void saveReport(ReportRequestDto dto) {
        Report report = new Report();

        User user = userRepository.findByLoginId(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("신고자 유저를 찾을 수 없습니다."));

        report.setUser(user);

        report.setTargetId(dto.getTargetId());
        report.setTargetNickName(dto.getTargetNickName());
        report.setTargetType(dto.getTargetType());
        report.setTitle(dto.getTitle());
        report.setReason(dto.getReason());
        report.setDate(LocalDateTime.now());

        reportRepository.save(report);
    }
}

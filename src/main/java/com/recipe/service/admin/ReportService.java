package com.recipe.service.admin;

import com.recipe.constant.TargetType;
import com.recipe.dto.admin.BannedListDto;
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
import org.springframework.transaction.annotation.Transactional;

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

    public List<BannedListDto> getBannedUsers() {
        List<Report> bannedReports = reportRepository.findAllBannedUsers(LocalDateTime.now());

        return bannedReports.stream().map(report -> {

            BannedListDto dto = new BannedListDto();

            // 이 부분에서 dto가 아직 선언되기 전에 bannedLoginId를 설정하려고 해서 에러 발생!
            // 따라서 dto 선언 위로 옮겨야 함
            String bannedLoginId = userRepository.findByNickName(report.getTargetNickName())
                    .map(User::getLoginId)
                    .orElse(report.getTargetNickName());
            dto.setBannedId(bannedLoginId);

            dto.setId(report.getId());
            dto.setBannedNickName(report.getTargetNickName());
            dto.setReason(report.getBannedReason());

            // targetUrl 만들기 (예시)
            String targetUrl = "#";
            if (report.getTargetType() != null && report.getTargetId() != null) {
                switch (report.getTargetType()) {
                    case RECIPE_POST:
                        targetUrl = "/recipe/" + report.getTargetId();
                        break;
                    case RECIPE_COMMENT:
                        // 댓글에 딸린 게시글 ID를 가져오는 로직 필요 (생략)
                        targetUrl = "/recipe/" + report.getTargetId() + "#comment-" + report.getTargetId();
                        break;
                    case COMMUNITY_POST:
                        targetUrl = "/community/" + report.getTargetId();
                        break;
                    case COMMUNITY_COMMENT:
                        // 댓글에 딸린 게시글 ID를 가져오는 로직 필요 (생략)
                        targetUrl = "/community/" + report.getTargetId() + "#comment-" + report.getTargetId();
                        break;
                }
            }
            dto.setTargetUrl(targetUrl);

            dto.setBannedDate(report.getBannedDate());
            dto.setBannedUntil(report.getBannedUntil());
            return dto;
        }).toList();
    }

    @Transactional
    public void banUsers(List<String> userIds, String reason) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(3);

        for (String loginId : userIds) {
            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음: " + loginId));

            boolean alreadyBanned = reportRepository.existsByUserAndBannedUntilAfter(user, now);
            if (alreadyBanned) continue;

            user.setBanned(true);
            user.setBanTime(now);
            userRepository.save(user);

            // 유저가 작성한 모든 글/댓글 ID 수집
            List<Long> recipeIds = recipeRepo.findAllByUser(user).stream().map(r -> r.getId()).toList();
            List<Long> recipeCommentIds = recipeCommentRepo.findAllByUser(user).stream().map(c -> c.getId()).toList();
            List<Long> postIds = postRepo.findAllByUser(user).stream().map(p -> p.getId()).toList();
            List<Long> postCommentIds = postCommentRepo.findAllByUser(user).stream().map(c -> c.getId()).toList();

            Report latestReport = null;

            // 레시피 게시글 신고 중 최신
            List<Report> recipeReports = reportRepository.findByTargetTypeAndTargetIdInOrderByDateDesc(TargetType.RECIPE_POST, recipeIds);
            if (!recipeReports.isEmpty()) latestReport = recipeReports.get(0);

            // 레시피 댓글 신고 중 최신
            List<Report> recipeCommentReports = reportRepository.findByTargetTypeAndTargetIdInOrderByDateDesc(TargetType.RECIPE_COMMENT, recipeCommentIds);
            if (!recipeCommentReports.isEmpty() && (latestReport == null || recipeCommentReports.get(0).getDate().isAfter(latestReport.getDate())))
                latestReport = recipeCommentReports.get(0);

            // 커뮤니티 게시글 신고 중 최신
            List<Report> postReports = reportRepository.findByTargetTypeAndTargetIdInOrderByDateDesc(TargetType.COMMUNITY_POST, postIds);
            if (!postReports.isEmpty() && (latestReport == null || postReports.get(0).getDate().isAfter(latestReport.getDate())))
                latestReport = postReports.get(0);

            // 커뮤니티 댓글 신고 중 최신
            List<Report> postCommentReports = reportRepository.findByTargetTypeAndTargetIdInOrderByDateDesc(TargetType.COMMUNITY_COMMENT, postCommentIds);
            if (!postCommentReports.isEmpty() && (latestReport == null || postCommentReports.get(0).getDate().isAfter(latestReport.getDate())))
                latestReport = postCommentReports.get(0);

            if (latestReport != null) {
                latestReport.setBannedDate(now);
                latestReport.setBannedUntil(endDate);
                latestReport.setBannedReason(reason);
                reportRepository.save(latestReport);
            }
        }
    }




}

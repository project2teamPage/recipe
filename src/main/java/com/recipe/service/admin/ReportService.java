package com.recipe.service.admin;

import com.recipe.dto.recipe.ReportListDto;
import com.recipe.entity.admin.Report;
import com.recipe.repository.admin.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    // 레포트 테이블에서 데이터 전부 가져오기 - 메서드
    public List<ReportListDto> getReports() {

        List<ReportListDto> reportListDtos = new ArrayList<>();
        // 레포지토리를 통해서 테이블의 데이터 가져오기
        List<Report> reportList = reportRepository.findAll();

        // 엔티티를 dto에 넘겨서 arraryList로 저장
        for(Report report : reportList) {
            // switch 이용해서 TargetType 에 따라  조회 할 테이블 결정 하여
            // 작성자 구하기
            // 작성자를 구해서 작성자의  닉네임을  ReportListDto.from (   ,  여기! );
            //  넣어주기

            ReportListDto reportListDto = ReportListDto.from(report, "정훈" );

            reportListDtos.add(reportListDto);
        }



        return reportListDtos;
    }
}

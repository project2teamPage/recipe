package com.recipe.repository.admin;

import com.recipe.entity.admin.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByOrderByDateDesc();
}

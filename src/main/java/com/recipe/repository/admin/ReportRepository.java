package com.recipe.repository.admin;

import com.recipe.entity.admin.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReportRepository extends JpaRepository<Report, Long> {

}

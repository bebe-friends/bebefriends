package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityPostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostReportRepository extends JpaRepository<CommunityPostReport, Long> {
}

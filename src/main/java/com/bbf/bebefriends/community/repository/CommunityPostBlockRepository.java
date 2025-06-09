package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityPostBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostBlockRepository extends JpaRepository<CommunityPostBlock, Long> {
}

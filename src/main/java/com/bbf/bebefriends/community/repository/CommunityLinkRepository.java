package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityLinkRepository extends JpaRepository<CommunityLink, Long> {
}

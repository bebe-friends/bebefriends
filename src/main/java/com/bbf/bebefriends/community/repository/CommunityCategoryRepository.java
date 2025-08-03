package com.bbf.bebefriends.community.repository;

import com.bbf.bebefriends.community.entity.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Long> {
    Optional<CommunityCategory> findByName(String name);
}

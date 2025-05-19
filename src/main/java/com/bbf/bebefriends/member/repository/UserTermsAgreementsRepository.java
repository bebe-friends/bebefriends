package com.bbf.bebefriends.member.repository;

import com.bbf.bebefriends.member.entity.UserTermsAgreements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTermsAgreementsRepository extends JpaRepository<UserTermsAgreements, Long> {

    Optional<UserTermsAgreements> findByUser_Uid(Long uid);
}
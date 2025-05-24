package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.hotdeal.entity.HotDealPostView;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotDealPostViewRepository extends JpaRepository<HotDealPostView, Long> {

    Optional<HotDealPostView> findByUserAndHotDealPost(User user, HotDealPost hotDealPost);
}

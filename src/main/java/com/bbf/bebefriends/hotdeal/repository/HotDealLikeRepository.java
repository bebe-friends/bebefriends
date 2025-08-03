package com.bbf.bebefriends.hotdeal.repository;

import com.bbf.bebefriends.hotdeal.entity.HotDeal;
import com.bbf.bebefriends.hotdeal.entity.HotDealLike;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotDealLikeRepository extends JpaRepository<HotDealLike, Long> {

    Optional<HotDealLike> findByHotDealPostAndUser(HotDealPost hotDealPost,User user);

    @Query("""
            SELECT l.hotDealPost.id
            FROM HotDealLike l
            WHERE l.user = :user
               AND l.hotDealPost IN :posts
           """)
    List<Long> findPostIdsByUserAndPostIn(
            @Param("user")  User user,
            @Param("posts") Collection<HotDealPost> posts
    );
}

package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;

@Entity
public class CommunityPostLike extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    public static CommunityPostLike createPostLike(User user, CommunityPost post) {
        CommunityPostLike communityPostLike = new CommunityPostLike();

        communityPostLike.user = user;
        communityPostLike.post = post;

        return communityPostLike;
    }
}

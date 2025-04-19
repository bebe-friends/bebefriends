package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class CommunityPostLike extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    public static CommunityPostLike createPostLike(Member member, CommunityPost post) {
        CommunityPostLike communityPostLike = new CommunityPostLike();

        communityPostLike.member = member;
        communityPostLike.post = post;

        return communityPostLike;
    }
}

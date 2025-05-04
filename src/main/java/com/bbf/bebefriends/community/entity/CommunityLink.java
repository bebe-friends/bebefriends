package com.bbf.bebefriends.community.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "community_links")
public class CommunityLink {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost communityPost;

    private String link;

    public static CommunityLink createLink(CommunityPost communityPost, String link) {
        CommunityLink communityLink = new CommunityLink();

        communityLink.communityPost = communityPost;
        communityLink.link = link;

        return communityLink;
    }
}

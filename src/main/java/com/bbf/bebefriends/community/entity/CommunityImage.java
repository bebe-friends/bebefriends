package com.bbf.bebefriends.community.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "files")
public class CommunityImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost communityPost;

    private String imgUrl;

    public static CommunityImage createImageFile(CommunityPost communityPost, String imgPath) {
        CommunityImage communityImage = new CommunityImage();

        communityImage.communityPost = communityPost;
        communityImage.imgUrl = imgPath;

        return communityImage;
    }
}

package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.community.dto.CommunityPostDTO;
import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: 게시물 신고, 게시물 실제 삭제, 게시물 인증, 하드 삭제 시 댓글 cascade 설정
@Entity
@Getter
@Table(name = "community_posts")
public class CommunityPost extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CommunityCategory category;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    private int viewCount;
    private int likeCount;
    private int commentCount;

    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLink> links = new ArrayList<>();

    private LocalDateTime isCertificated;
    private LocalDateTime isReported;
    private LocalDateTime deletedAt;

    public void addImage(CommunityImage image) {
        images.add(image);
    }

    public void addLink(CommunityLink link) {
        links.add(link);
    }

    // 게시글 생성
    public static CommunityPost createPost(User user, CommunityCategory category, CommunityPostDTO.CreatePostRequest postCreateRequest) {
        CommunityPost post = new CommunityPost();

        post.user = user;
        post.category = category;
        post.title = postCreateRequest.getTitle();
        post.content = postCreateRequest.getContent();
        post.viewCount = 0;
        post.likeCount = 0;
        post.commentCount = 0;
        post.deletedAt = null;
        post.isCertificated = null;
        post.isReported = null;

        return post;
    }

    // 게시글 수정
    public void updatePost(CommunityCategory category, CommunityPostDTO.UpdatePostRequest postUpdateRequest) {
        this.category = category;
        this.title = postUpdateRequest.getTitle();
        this.content = postUpdateRequest.getContent();
    }

    // 게시글 삭제
    public void setDeletedAt() {
        this.deletedAt = LocalDateTime.now().withNano(0);
    }

    // 게시글 조회수 증가
    public void increaseViewCount() {
        this.viewCount++;
    }

    // 게시물 좋아요
    public void increaseLikeCount() {
        this.likeCount++;
    }

    // 게시물 좋아요 취소
    public void decreaseLikeCount() {
        this.likeCount--;
    }

    // 댓글 생성
    public void increaseCommentCount() {
        this.commentCount++;
    }

    // 댓글 삭제
    public void decreaseCommentCount() {
        this.commentCount--;
    }
}

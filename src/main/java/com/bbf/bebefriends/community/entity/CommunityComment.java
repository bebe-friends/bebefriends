package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "community_comments")
public class CommunityComment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommunityComment parent;
//    @OneToMany(mappedBy = "parent")
//    private List<CommunityComment> subComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;
    private LocalDateTime deletedAt;

    public static CommunityComment createComment(CommunityPost post, User user, CommunityComment parent, CommunityCommentDTO.CreateCommentRequest createCommentRequest) {
        CommunityComment comment = new CommunityComment();

        comment.post = post;
        comment.user = user;
        comment.parent = parent;
        comment.content = createCommentRequest.getContent();

        return comment;
    }

//    public static CommunityComment createReply(CommunityPost post, User user, CommunityComment parent, CommunityCommentDTO.CreateCommentRequest createCommentRequest) {
//        CommunityComment comment = new CommunityComment();
//
//        comment.post = post;
//        comment.user = user;
//        comment.parent = parent;
//        comment.content = createCommentRequest.getContent();
//
//        return comment;
//    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void setDeletedAt() {
        this.deletedAt = LocalDateTime.now().withNano(0);
    }
}

package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "hot_deal_post")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 핫딜 게시글 식별자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                      // 회원 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_id")
    private HotDeal hotDeal;                // 핫딜 식별자

    private String title;                   // 제목

    private String content;                 // 내용

    private String link;                    // 링크

    private String imgPath;                 // 이미지 경로

    private String status;                  // 핫딜 상태

    private String age;                    // 나이

    private int viewCount;                  // 조회수

    private int likeCount;                  // 좋아요 수

    private LocalDateTime deletedAt;        // 삭제일자

    public static HotDealPost createHotDealPost(User user,
                                                HotDeal hotDeal,
                                                HotDealPostDto.CreateHotDealPostRequest request,
                                                String links,
                                                String imgPaths,
                                                String age) {
        HotDealPost hotDealPost = new HotDealPost();

        hotDealPost.user = user;
        hotDealPost.hotDeal = hotDeal;
        hotDealPost.title = request.getTitle();
        hotDealPost.content = request.getContent();
        hotDealPost.link = links;
        hotDealPost.imgPath = imgPaths;
        hotDealPost.status = request.getStatus();
        hotDealPost.age = age;

        return hotDealPost;
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

    public void updatePost(HotDealPostDto.UpdateHotDealPostRequest request,
                           String links,
                           String imgPaths,
                           String age) {
        this.content = request.getContent();
        this.link = links;
        this.imgPath = imgPaths;
        this.age = age;
    }

    public void updateHotDeal(HotDeal hotDeal) {
        this.hotDeal = hotDeal;
    }

    public void setDeletedAt() {
        this.deletedAt = LocalDateTime.now();
    }

}

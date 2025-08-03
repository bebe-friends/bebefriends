package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "hot_deal_like")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 핫딜 좋아요 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_post_id")
    private HotDealPost hotDealPost;        // 핫딜 게시글 식별자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                      // 사용자

}

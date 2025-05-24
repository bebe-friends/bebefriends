package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "hot_deal_post_view")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDealPostView extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 핫딜 게시글 조회 식별자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                      // 유저 식별자

    @ManyToOne
    @JoinColumn(name = "hot_deal_post_id")
    private HotDealPost hotDealPost;        // 핫딜 게시글 식별자

}

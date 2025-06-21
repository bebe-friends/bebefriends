package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "hot_deal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotDeal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hot_deal_category_id")
    private HotDealCategory hotDealCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String content;

    private String imgPath;

    private String unit;

    private Boolean status;

    private int viewCount;
    private int likeCount;
    private int commentCount;

    private LocalDateTime deletedAt;


    public static HotDeal createHotDeal(User user,
                                        HotDealCategory category,
                                        HotDealDto.HotDealRequest request,
                                        List<MultipartFile> imgPath
    ) {
        HotDeal hotDeal = new HotDeal();

        hotDeal.user = user;
        hotDeal.content = request.content();
        hotDeal.viewCount = 0;
        hotDeal.likeCount = 0;
        hotDeal.commentCount = 0;
        hotDeal.deletedAt = null;
        hotDeal.status = false;
        hotDeal.hotDealCategory = category;
        hotDeal.name = request.name();

        // todo. 이미지 작업

        return hotDeal;
    }

    // todo. 핫딜 게시글 관련 기능들
}

package com.bbf.bebefriends.hotdeal.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.dto.HotDealDto;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    @JoinColumn(name = "hot_deal_category_id", unique = true, nullable = false)
    private HotDealCategory hotDealCategory;

    @ManyToOne
    @JoinColumn(name = "detail_category_id", unique = true, nullable = false)
    private HotDealCategory detailCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;
    private String imgPath;
    private String unit;

    public static HotDeal createHotDeal(User user,
                                        HotDealCategory category,
                                        HotDealCategory detailCategory,
                                        HotDealDto.HotDealRequest request,
                                        List<MultipartFile> imgPath
    ) {
        if (user == null || category == null || request.name() == null ||
                request.content() == null || request.unit() == null) {
            throw new IllegalArgumentException("필수 필드가 누락되었습니다.");
        }

        HotDeal hotDeal = new HotDeal();
        hotDeal.user = user;
        hotDeal.content = request.content();
        hotDeal.hotDealCategory = category;
        hotDeal.detailCategory = detailCategory;
        hotDeal.unit = request.unit();

        hotDeal.name = request.name();
        // todo. 이미지 작업

        return hotDeal;
    }
}

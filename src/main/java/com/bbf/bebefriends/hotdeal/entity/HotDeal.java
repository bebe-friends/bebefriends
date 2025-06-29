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

    @OneToOne
    @JoinColumn(name = "detail_category_id", unique = true)
    private HotDealCategory detailCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String content;
    private String imgPath;
    private String unit;

    public static HotDeal createHotDeal(User user,
                                        HotDealCategory category,
                                        HotDealDto.HotDealRequest request,
                                        List<MultipartFile> imgPath
    ) {
        HotDeal hotDeal = new HotDeal();

        hotDeal.user = user;
        hotDeal.content = request.content();
        hotDeal.hotDealCategory = category;
        hotDeal.detailCategory = null;
        hotDeal.name = request.name();
        // todo. 이미지 작업

        return hotDeal;
    }
}

package com.bbf.bebefriends.member.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.hotdeal.entity.HotDealCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_hotdeal_notification")
public class UserHotdealNotification extends BaseEntity {

    @Id
    private Long uid;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "uid")
    private User user;

    @Column(nullable = false)
    private boolean age_0;

    @Column(nullable = false)
    private boolean age_1;

    @Column(nullable = false)
    private boolean age_2;

    @Column(nullable = false)
    private boolean age_3;

    @Column(nullable = false)
    private boolean age_4;

    @Column(nullable = false)
    private boolean age_5;

    @Column(nullable = false)
    private boolean age_6;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_hotdeal_categories",
            joinColumns = @JoinColumn(name = "user_hotdeal_notification_id"),
            inverseJoinColumns = @JoinColumn(name = "hot_deal_category_id")
    )
    private List<HotDealCategory> preferredCategories;

    public static UserHotdealNotification of(
            User user,
            boolean... ages
    ) {
        UserHotdealNotification settings = new UserHotdealNotification();
        settings.setUser(user);
        settings.setPreferredCategories(new ArrayList<>());

        if (ages.length > 0) settings.setAge_0(ages.length > 0 ? ages[0] : false);
        if (ages.length > 1) settings.setAge_1(ages.length > 1 ? ages[1] : false);
        if (ages.length > 2) settings.setAge_2(ages.length > 2 ? ages[2] : false);
        if (ages.length > 3) settings.setAge_3(ages.length > 3 ? ages[3] : false);
        if (ages.length > 4) settings.setAge_4(ages.length > 4 ? ages[4] : false);
        if (ages.length > 5) settings.setAge_5(ages.length > 5 ? ages[5] : false);
        if (ages.length > 6) settings.setAge_6(ages.length > 6 ? ages[6] : false);

        return settings;
    }

    public static UserHotdealNotification of(
            User user
    ) {
        return of(user, false, false, false, false, false, false, false);
    }

}

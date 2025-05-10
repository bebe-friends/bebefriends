package com.bbf.bebefriends.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_notification_settings")
public class UserNotificationSettings extends BaseTimeEntity {

    @Id
    private String uid;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "uid")
    private User user;

    @Column(nullable = false)
    private boolean hotDealNotificationAgreement = false;

    @Column(nullable = false)
    private boolean hotDealNightNotificationAgreement = false;

    @Column(nullable = false)
    private boolean marketingNotificationAgreement = false;

    @Column(nullable = false)
    private boolean marketingNightNotificationAgreement = false;

    @Column(nullable = false)
    private boolean commentNotificationAgreement = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public static UserNotificationSettings of(
            User user,
            boolean hotDeal,
            boolean hotDealNight,
            boolean marketing,
            boolean marketingNight,
            boolean comment
            ) {
        UserNotificationSettings settings = new UserNotificationSettings();
        settings.setUser(user);
        settings.setHotDealNotificationAgreement(hotDeal);
        settings.setHotDealNightNotificationAgreement(hotDealNight);
        settings.setMarketingNotificationAgreement(marketing);
        settings.setMarketingNightNotificationAgreement(marketingNight);
        settings.setCommentNotificationAgreement(comment);
        return settings;
    }
}

package com.bbf.bebefriends.member.entity;

import com.bbf.bebefriends.community.entity.CommunityPostBlock;
import com.bbf.bebefriends.community.entity.CommunityUserBlock;
import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone;

    @Column
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Oauth2UserInfo oauth2UserInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserNotificationSettings notificationSettings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserTermsAgreements termsAgreements;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserHotdealNotification notification;

    private String fcmToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityPostBlock> communityPostBlock;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityUserBlock> communityUserBlocks;

    public static User createGuestUser() {
        User guestUser = new User();
        guestUser.setNickname("Guest_" + System.currentTimeMillis());
        guestUser.setEmail(null);
        guestUser.setPhone(null);
        guestUser.setRole(UserRole.GUEST);
        guestUser.setFcmToken(null);
        return guestUser;
    }
}
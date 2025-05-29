package com.bbf.bebefriends.member.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Oauth2UserInfo oauth2UserInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserNotificationSettings notificationSettings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserTermsAgreements termsAgreements;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserHotdealNotification notification;

    private String fcmToken;

    @ElementCollection
    private List<String> blockedUsers = new ArrayList<>();

    @ElementCollection
    private List<String> blockedPosts = new ArrayList<>();

    public static User createGuestUser() {
        User guestUser = new User();
        guestUser.setNickname("Guest_" + System.currentTimeMillis());
        guestUser.setEmail(null); // Temporary users might not have an email
        guestUser.setPhone(null); // Temporary users might not have a phone number
        guestUser.setRole(UserRole.GUEST); // Assign the GUEST role
        guestUser.setFcmToken(null); // FCM token can be null for temporary users
        return guestUser;
    }
}
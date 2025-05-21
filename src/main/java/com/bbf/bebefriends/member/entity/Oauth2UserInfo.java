package com.bbf.bebefriends.member.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "oauth2_user_info")
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2UserInfo extends BaseEntity {

    @Id
    private Long uid;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "uid")
    private User user;

    @Column
    private Long oauthId;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    public static Oauth2UserInfo of(User user, Long oauthId) {
        return new Oauth2UserInfo(user.getUid(), user, oauthId, OauthProvider.KAKAO);
    }
}

package com.bbf.bebefriends.community.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
import com.bbf.bebefriends.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommunityUserBlock extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 차단한 주체
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 차단된 대상
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_user_id")
    private User blockedUser;

    public static CommunityUserBlock createUserBlock(User user, User blockedUser) {
        CommunityUserBlock userBlock = new CommunityUserBlock();

        userBlock.user = user;
        userBlock.blockedUser = blockedUser;

        return userBlock;
    }
}

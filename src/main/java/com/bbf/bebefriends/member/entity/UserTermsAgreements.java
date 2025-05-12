package com.bbf.bebefriends.member.entity;

import com.bbf.bebefriends.global.entity.BaseEntity;
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
@Table(name = "user_terms_agreements")
public class UserTermsAgreements extends BaseEntity {

    @Id
    private Long uid;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "uid")
    private User user;

    @Column(nullable = false)
    private LocalDateTime agreement;

    @Column(nullable = false)
    private LocalDateTime privatePolicy;

    @Column(nullable = false)
    private Boolean age;


    public static UserTermsAgreements of(User user,
                                         LocalDateTime agreement,
                                         LocalDateTime privatePolicy,
                                         Boolean age
    ) {
        return new UserTermsAgreements(user.getUid(), user, agreement, privatePolicy, age);
    }
}

package com.bbf.bebefriends.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 41664854L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final ListPath<com.bbf.bebefriends.community.entity.CommunityPostBlock, com.bbf.bebefriends.community.entity.QCommunityPostBlock> communityPostBlock = this.<com.bbf.bebefriends.community.entity.CommunityPostBlock, com.bbf.bebefriends.community.entity.QCommunityPostBlock>createList("communityPostBlock", com.bbf.bebefriends.community.entity.CommunityPostBlock.class, com.bbf.bebefriends.community.entity.QCommunityPostBlock.class, PathInits.DIRECT2);

    public final ListPath<com.bbf.bebefriends.community.entity.CommunityUserBlock, com.bbf.bebefriends.community.entity.QCommunityUserBlock> communityUserBlocks = this.<com.bbf.bebefriends.community.entity.CommunityUserBlock, com.bbf.bebefriends.community.entity.QCommunityUserBlock>createList("communityUserBlocks", com.bbf.bebefriends.community.entity.CommunityUserBlock.class, com.bbf.bebefriends.community.entity.QCommunityUserBlock.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final StringPath fcmToken = createString("fcmToken");

    public final StringPath nickname = createString("nickname");

    public final QUserHotdealNotification notification;

    public final QUserNotificationSettings notificationSettings;

    public final QOauth2UserInfo oauth2UserInfo;

    public final StringPath phone = createString("phone");

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public final QUserTermsAgreements termsAgreements;

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notification = inits.isInitialized("notification") ? new QUserHotdealNotification(forProperty("notification"), inits.get("notification")) : null;
        this.notificationSettings = inits.isInitialized("notificationSettings") ? new QUserNotificationSettings(forProperty("notificationSettings"), inits.get("notificationSettings")) : null;
        this.oauth2UserInfo = inits.isInitialized("oauth2UserInfo") ? new QOauth2UserInfo(forProperty("oauth2UserInfo"), inits.get("oauth2UserInfo")) : null;
        this.termsAgreements = inits.isInitialized("termsAgreements") ? new QUserTermsAgreements(forProperty("termsAgreements"), inits.get("termsAgreements")) : null;
    }

}


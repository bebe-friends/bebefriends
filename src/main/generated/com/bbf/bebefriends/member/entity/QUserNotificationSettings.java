package com.bbf.bebefriends.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserNotificationSettings is a Querydsl query type for UserNotificationSettings
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserNotificationSettings extends EntityPathBase<UserNotificationSettings> {

    private static final long serialVersionUID = 1742372004L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserNotificationSettings userNotificationSettings = new QUserNotificationSettings("userNotificationSettings");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final BooleanPath commentNotificationAgreement = createBoolean("commentNotificationAgreement");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath hotDealNightNotificationAgreement = createBoolean("hotDealNightNotificationAgreement");

    public final BooleanPath hotDealNotificationAgreement = createBoolean("hotDealNotificationAgreement");

    public final BooleanPath marketingNightNotificationAgreement = createBoolean("marketingNightNotificationAgreement");

    public final BooleanPath marketingNotificationAgreement = createBoolean("marketingNotificationAgreement");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final QUser user;

    public QUserNotificationSettings(String variable) {
        this(UserNotificationSettings.class, forVariable(variable), INITS);
    }

    public QUserNotificationSettings(Path<? extends UserNotificationSettings> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserNotificationSettings(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserNotificationSettings(PathMetadata metadata, PathInits inits) {
        this(UserNotificationSettings.class, metadata, inits);
    }

    public QUserNotificationSettings(Class<? extends UserNotificationSettings> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


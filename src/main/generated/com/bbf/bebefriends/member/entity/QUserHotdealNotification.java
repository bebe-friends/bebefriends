package com.bbf.bebefriends.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHotdealNotification is a Querydsl query type for UserHotdealNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserHotdealNotification extends EntityPathBase<UserHotdealNotification> {

    private static final long serialVersionUID = 585587854L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHotdealNotification userHotdealNotification = new QUserHotdealNotification("userHotdealNotification");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final BooleanPath age_0 = createBoolean("age_0");

    public final BooleanPath age_1 = createBoolean("age_1");

    public final BooleanPath age_2 = createBoolean("age_2");

    public final BooleanPath age_3 = createBoolean("age_3");

    public final BooleanPath age_4 = createBoolean("age_4");

    public final BooleanPath age_5 = createBoolean("age_5");

    public final BooleanPath age_6 = createBoolean("age_6");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.bbf.bebefriends.hotdeal.entity.HotDealCategory, com.bbf.bebefriends.hotdeal.entity.QHotDealCategory> preferredCategories = this.<com.bbf.bebefriends.hotdeal.entity.HotDealCategory, com.bbf.bebefriends.hotdeal.entity.QHotDealCategory>createList("preferredCategories", com.bbf.bebefriends.hotdeal.entity.HotDealCategory.class, com.bbf.bebefriends.hotdeal.entity.QHotDealCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final QUser user;

    public QUserHotdealNotification(String variable) {
        this(UserHotdealNotification.class, forVariable(variable), INITS);
    }

    public QUserHotdealNotification(Path<? extends UserHotdealNotification> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHotdealNotification(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHotdealNotification(PathMetadata metadata, PathInits inits) {
        this(UserHotdealNotification.class, metadata, inits);
    }

    public QUserHotdealNotification(Class<? extends UserHotdealNotification> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


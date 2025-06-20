package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDeal is a Querydsl query type for HotDeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDeal extends EntityPathBase<HotDeal> {

    private static final long serialVersionUID = 609660183L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDeal hotDeal = new QHotDeal("hotDeal");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final QHotDealCategory hotDealCategory;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgPath = createString("imgPath");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath name = createString("name");

    public final BooleanPath status = createBoolean("status");

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QHotDeal(String variable) {
        this(HotDeal.class, forVariable(variable), INITS);
    }

    public QHotDeal(Path<? extends HotDeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDeal(PathMetadata metadata, PathInits inits) {
        this(HotDeal.class, metadata, inits);
    }

    public QHotDeal(Class<? extends HotDeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotDealCategory = inits.isInitialized("hotDealCategory") ? new QHotDealCategory(forProperty("hotDealCategory"), inits.get("hotDealCategory")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealPost is a Querydsl query type for HotDealPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealPost extends EntityPathBase<HotDealPost> {

    private static final long serialVersionUID = -1868409257L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealPost hotDealPost = new QHotDealPost("hotDealPost");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final QHotDeal hotDeal;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgPath = createString("imgPath");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final StringPath link = createString("link");

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QHotDealPost(String variable) {
        this(HotDealPost.class, forVariable(variable), INITS);
    }

    public QHotDealPost(Path<? extends HotDealPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealPost(PathMetadata metadata, PathInits inits) {
        this(HotDealPost.class, metadata, inits);
    }

    public QHotDealPost(Class<? extends HotDealPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotDeal = inits.isInitialized("hotDeal") ? new QHotDeal(forProperty("hotDeal"), inits.get("hotDeal")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


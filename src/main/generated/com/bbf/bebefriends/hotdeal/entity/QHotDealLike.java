package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealLike is a Querydsl query type for HotDealLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealLike extends EntityPathBase<HotDealLike> {

    private static final long serialVersionUID = -1868534450L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealLike hotDealLike = new QHotDealLike("hotDealLike");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QHotDealPost hotDealPost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QHotDealLike(String variable) {
        this(HotDealLike.class, forVariable(variable), INITS);
    }

    public QHotDealLike(Path<? extends HotDealLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealLike(PathMetadata metadata, PathInits inits) {
        this(HotDealLike.class, metadata, inits);
    }

    public QHotDealLike(Class<? extends HotDealLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotDealPost = inits.isInitialized("hotDealPost") ? new QHotDealPost(forProperty("hotDealPost"), inits.get("hotDealPost")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealComment is a Querydsl query type for HotDealComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealComment extends EntityPathBase<HotDealComment> {

    private static final long serialVersionUID = -1957281464L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealComment hotDealComment = new QHotDealComment("hotDealComment");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final QHotDealPost hotDealPost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QHotDealComment repliedComment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QHotDealComment(String variable) {
        this(HotDealComment.class, forVariable(variable), INITS);
    }

    public QHotDealComment(Path<? extends HotDealComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealComment(PathMetadata metadata, PathInits inits) {
        this(HotDealComment.class, metadata, inits);
    }

    public QHotDealComment(Class<? extends HotDealComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotDealPost = inits.isInitialized("hotDealPost") ? new QHotDealPost(forProperty("hotDealPost"), inits.get("hotDealPost")) : null;
        this.repliedComment = inits.isInitialized("repliedComment") ? new QHotDealComment(forProperty("repliedComment"), inits.get("repliedComment")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


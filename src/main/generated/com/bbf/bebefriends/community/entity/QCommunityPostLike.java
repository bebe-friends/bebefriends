package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityPostLike is a Querydsl query type for CommunityPostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityPostLike extends EntityPathBase<CommunityPostLike> {

    private static final long serialVersionUID = 1978412014L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityPostLike communityPostLike = new QCommunityPostLike("communityPostLike");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCommunityPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QCommunityPostLike(String variable) {
        this(CommunityPostLike.class, forVariable(variable), INITS);
    }

    public QCommunityPostLike(Path<? extends CommunityPostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityPostLike(PathMetadata metadata, PathInits inits) {
        this(CommunityPostLike.class, metadata, inits);
    }

    public QCommunityPostLike(Class<? extends CommunityPostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QCommunityPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


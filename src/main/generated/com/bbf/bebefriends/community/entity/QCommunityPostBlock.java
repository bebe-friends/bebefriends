package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityPostBlock is a Querydsl query type for CommunityPostBlock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityPostBlock extends EntityPathBase<CommunityPostBlock> {

    private static final long serialVersionUID = 1192088342L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityPostBlock communityPostBlock = new QCommunityPostBlock("communityPostBlock");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCommunityPost post;

    public final EnumPath<PostBlockReason> reason = createEnum("reason", PostBlockReason.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QCommunityPostBlock(String variable) {
        this(CommunityPostBlock.class, forVariable(variable), INITS);
    }

    public QCommunityPostBlock(Path<? extends CommunityPostBlock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityPostBlock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityPostBlock(PathMetadata metadata, PathInits inits) {
        this(CommunityPostBlock.class, metadata, inits);
    }

    public QCommunityPostBlock(Class<? extends CommunityPostBlock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QCommunityPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


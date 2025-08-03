package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityCommentBlock is a Querydsl query type for CommunityCommentBlock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityCommentBlock extends EntityPathBase<CommunityCommentBlock> {

    private static final long serialVersionUID = 444147077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityCommentBlock communityCommentBlock = new QCommunityCommentBlock("communityCommentBlock");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final QCommunityComment comment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QCommunityCommentBlock(String variable) {
        this(CommunityCommentBlock.class, forVariable(variable), INITS);
    }

    public QCommunityCommentBlock(Path<? extends CommunityCommentBlock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityCommentBlock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityCommentBlock(PathMetadata metadata, PathInits inits) {
        this(CommunityCommentBlock.class, metadata, inits);
    }

    public QCommunityCommentBlock(Class<? extends CommunityCommentBlock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QCommunityComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityUserBlock is a Querydsl query type for CommunityUserBlock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityUserBlock extends EntityPathBase<CommunityUserBlock> {

    private static final long serialVersionUID = -471350581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityUserBlock communityUserBlock = new QCommunityUserBlock("communityUserBlock");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final com.bbf.bebefriends.member.entity.QUser blockedUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QCommunityUserBlock(String variable) {
        this(CommunityUserBlock.class, forVariable(variable), INITS);
    }

    public QCommunityUserBlock(Path<? extends CommunityUserBlock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityUserBlock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityUserBlock(PathMetadata metadata, PathInits inits) {
        this(CommunityUserBlock.class, metadata, inits);
    }

    public QCommunityUserBlock(Class<? extends CommunityUserBlock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.blockedUser = inits.isInitialized("blockedUser") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("blockedUser"), inits.get("blockedUser")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


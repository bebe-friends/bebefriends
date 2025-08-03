package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityReport is a Querydsl query type for CommunityReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityReport extends EntityPathBase<CommunityReport> {

    private static final long serialVersionUID = -1062859125L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityReport communityReport = new QCommunityReport("communityReport");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final QCommunityComment comment;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCommunityPost post;

    public final EnumPath<BlockReason> reason = createEnum("reason", BlockReason.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QCommunityReport(String variable) {
        this(CommunityReport.class, forVariable(variable), INITS);
    }

    public QCommunityReport(Path<? extends CommunityReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityReport(PathMetadata metadata, PathInits inits) {
        this(CommunityReport.class, metadata, inits);
    }

    public QCommunityReport(Class<? extends CommunityReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QCommunityComment(forProperty("comment"), inits.get("comment")) : null;
        this.post = inits.isInitialized("post") ? new QCommunityPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


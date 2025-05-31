package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityPost is a Querydsl query type for CommunityPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityPost extends EntityPathBase<CommunityPost> {

    private static final long serialVersionUID = -175457353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityPost communityPost = new QCommunityPost("communityPost");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final QCommunityCategory category;

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<CommunityImage, QCommunityImage> images = this.<CommunityImage, QCommunityImage>createList("images", CommunityImage.class, QCommunityImage.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> isCertificated = createDateTime("isCertificated", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> isReported = createDateTime("isReported", java.time.LocalDateTime.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final ListPath<CommunityLink, QCommunityLink> links = this.<CommunityLink, QCommunityLink>createList("links", CommunityLink.class, QCommunityLink.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QCommunityPost(String variable) {
        this(CommunityPost.class, forVariable(variable), INITS);
    }

    public QCommunityPost(Path<? extends CommunityPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityPost(PathMetadata metadata, PathInits inits) {
        this(CommunityPost.class, metadata, inits);
    }

    public QCommunityPost(Class<? extends CommunityPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCommunityCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


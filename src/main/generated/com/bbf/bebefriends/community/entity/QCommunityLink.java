package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityLink is a Querydsl query type for CommunityLink
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityLink extends EntityPathBase<CommunityLink> {

    private static final long serialVersionUID = -175582447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityLink communityLink = new QCommunityLink("communityLink");

    public final QCommunityPost communityPost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public QCommunityLink(String variable) {
        this(CommunityLink.class, forVariable(variable), INITS);
    }

    public QCommunityLink(Path<? extends CommunityLink> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityLink(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityLink(PathMetadata metadata, PathInits inits) {
        this(CommunityLink.class, metadata, inits);
    }

    public QCommunityLink(Class<? extends CommunityLink> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.communityPost = inits.isInitialized("communityPost") ? new QCommunityPost(forProperty("communityPost"), inits.get("communityPost")) : null;
    }

}


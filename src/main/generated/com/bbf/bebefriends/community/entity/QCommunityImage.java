package com.bbf.bebefriends.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityImage is a Querydsl query type for CommunityImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityImage extends EntityPathBase<CommunityImage> {

    private static final long serialVersionUID = -1150752476L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityImage communityImage = new QCommunityImage("communityImage");

    public final QCommunityPost communityPost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    public QCommunityImage(String variable) {
        this(CommunityImage.class, forVariable(variable), INITS);
    }

    public QCommunityImage(Path<? extends CommunityImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityImage(PathMetadata metadata, PathInits inits) {
        this(CommunityImage.class, metadata, inits);
    }

    public QCommunityImage(Class<? extends CommunityImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.communityPost = inits.isInitialized("communityPost") ? new QCommunityPost(forProperty("communityPost"), inits.get("communityPost")) : null;
    }

}


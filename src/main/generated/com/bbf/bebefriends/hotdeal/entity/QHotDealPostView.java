package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealPostView is a Querydsl query type for HotDealPostView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealPostView extends EntityPathBase<HotDealPostView> {

    private static final long serialVersionUID = 813302172L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealPostView hotDealPostView = new QHotDealPostView("hotDealPostView");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QHotDealPost hotDealPost;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QHotDealPostView(String variable) {
        this(HotDealPostView.class, forVariable(variable), INITS);
    }

    public QHotDealPostView(Path<? extends HotDealPostView> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealPostView(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealPostView(PathMetadata metadata, PathInits inits) {
        this(HotDealPostView.class, metadata, inits);
    }

    public QHotDealPostView(Class<? extends HotDealPostView> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotDealPost = inits.isInitialized("hotDealPost") ? new QHotDealPost(forProperty("hotDealPost"), inits.get("hotDealPost")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


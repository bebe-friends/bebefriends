package com.bbf.bebefriends.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOauth2UserInfo is a Querydsl query type for Oauth2UserInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOauth2UserInfo extends EntityPathBase<Oauth2UserInfo> {

    private static final long serialVersionUID = -1879832449L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOauth2UserInfo oauth2UserInfo = new QOauth2UserInfo("oauth2UserInfo");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> oauthId = createNumber("oauthId", Long.class);

    public final EnumPath<OauthProvider> provider = createEnum("provider", OauthProvider.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final QUser user;

    public QOauth2UserInfo(String variable) {
        this(Oauth2UserInfo.class, forVariable(variable), INITS);
    }

    public QOauth2UserInfo(Path<? extends Oauth2UserInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOauth2UserInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOauth2UserInfo(PathMetadata metadata, PathInits inits) {
        this(Oauth2UserInfo.class, metadata, inits);
    }

    public QOauth2UserInfo(Class<? extends Oauth2UserInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


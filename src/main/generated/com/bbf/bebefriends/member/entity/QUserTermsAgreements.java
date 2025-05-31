package com.bbf.bebefriends.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserTermsAgreements is a Querydsl query type for UserTermsAgreements
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserTermsAgreements extends EntityPathBase<UserTermsAgreements> {

    private static final long serialVersionUID = -1854572454L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserTermsAgreements userTermsAgreements = new QUserTermsAgreements("userTermsAgreements");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    public final BooleanPath age = createBoolean("age");

    public final DateTimePath<java.time.LocalDateTime> agreement = createDateTime("agreement", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> privatePolicy = createDateTime("privatePolicy", java.time.LocalDateTime.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final QUser user;

    public QUserTermsAgreements(String variable) {
        this(UserTermsAgreements.class, forVariable(variable), INITS);
    }

    public QUserTermsAgreements(Path<? extends UserTermsAgreements> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserTermsAgreements(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserTermsAgreements(PathMetadata metadata, PathInits inits) {
        this(UserTermsAgreements.class, metadata, inits);
    }

    public QUserTermsAgreements(Class<? extends UserTermsAgreements> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


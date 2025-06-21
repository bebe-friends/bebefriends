package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealRecord is a Querydsl query type for HotDealRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealRecord extends EntityPathBase<HotDealRecord> {

    private static final long serialVersionUID = -197420984L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealRecord hotDealRecord = new QHotDealRecord("hotDealRecord");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final QHotDeal hotDeal;

    public final NumberPath<Integer> hotDealPrice = createNumber("hotDealPrice", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath note = createString("note");

    public final NumberPath<Integer> searchPrice = createNumber("searchPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QHotDealRecord(String variable) {
        this(HotDealRecord.class, forVariable(variable), INITS);
    }

    public QHotDealRecord(Path<? extends HotDealRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealRecord(PathMetadata metadata, PathInits inits) {
        this(HotDealRecord.class, metadata, inits);
    }

    public QHotDealRecord(Class<? extends HotDealRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hotDeal = inits.isInitialized("hotDeal") ? new QHotDeal(forProperty("hotDeal"), inits.get("hotDeal")) : null;
    }

}


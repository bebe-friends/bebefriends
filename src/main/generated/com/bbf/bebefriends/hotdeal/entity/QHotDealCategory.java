package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealCategory is a Querydsl query type for HotDealCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealCategory extends EntityPathBase<HotDealCategory> {

    private static final long serialVersionUID = 106743605L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealCategory hotDealCategory = new QHotDealCategory("hotDealCategory");

    public final com.bbf.bebefriends.global.entity.QBaseEntity _super = new com.bbf.bebefriends.global.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QHotDealCategory parentCategory;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QHotDealCategory(String variable) {
        this(HotDealCategory.class, forVariable(variable), INITS);
    }

    public QHotDealCategory(Path<? extends HotDealCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealCategory(PathMetadata metadata, PathInits inits) {
        this(HotDealCategory.class, metadata, inits);
    }

    public QHotDealCategory(Class<? extends HotDealCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentCategory = inits.isInitialized("parentCategory") ? new QHotDealCategory(forProperty("parentCategory"), inits.get("parentCategory")) : null;
    }

}


package com.bbf.bebefriends.hotdeal.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHotDealCommentBlock is a Querydsl query type for HotDealCommentBlock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHotDealCommentBlock extends EntityPathBase<HotDealCommentBlock> {

    private static final long serialVersionUID = -2078531547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHotDealCommentBlock hotDealCommentBlock = new QHotDealCommentBlock("hotDealCommentBlock");

    public final QHotDealComment comment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.bbf.bebefriends.member.entity.QUser user;

    public QHotDealCommentBlock(String variable) {
        this(HotDealCommentBlock.class, forVariable(variable), INITS);
    }

    public QHotDealCommentBlock(Path<? extends HotDealCommentBlock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHotDealCommentBlock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHotDealCommentBlock(PathMetadata metadata, PathInits inits) {
        this(HotDealCommentBlock.class, metadata, inits);
    }

    public QHotDealCommentBlock(Class<? extends HotDealCommentBlock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QHotDealComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.bbf.bebefriends.member.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}


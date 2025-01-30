package chungbazi.chungbazi_be.domain.community.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityImages is a Querydsl query type for CommunityImages
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityImages extends EntityPathBase<CommunityImages> {

    private static final long serialVersionUID = -1102213095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityImages communityImages = new QCommunityImages("communityImages");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> orderIndex = createNumber("orderIndex", Integer.class);

    public final QPost post;

    public final StringPath url = createString("url");

    public QCommunityImages(String variable) {
        this(CommunityImages.class, forVariable(variable), INITS);
    }

    public QCommunityImages(Path<? extends CommunityImages> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityImages(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityImages(PathMetadata metadata, PathInits inits) {
        this(CommunityImages.class, metadata, inits);
    }

    public QCommunityImages(Class<? extends CommunityImages> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}


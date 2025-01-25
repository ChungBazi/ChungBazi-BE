package chungbazi.chungbazi_be.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddition is a Querydsl query type for Addition
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddition extends EntityPathBase<Addition> {

    private static final long serialVersionUID = 578550402L;

    public static final QAddition addition = new QAddition("addition");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QAddition(String variable) {
        super(Addition.class, forVariable(variable));
    }

    public QAddition(Path<? extends Addition> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddition(PathMetadata metadata) {
        super(Addition.class, metadata);
    }

}


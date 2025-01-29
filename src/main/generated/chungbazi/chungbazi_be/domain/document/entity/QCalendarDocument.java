package chungbazi.chungbazi_be.domain.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCalendarDocument is a Querydsl query type for CalendarDocument
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendarDocument extends EntityPathBase<CalendarDocument> {

    private static final long serialVersionUID = -959233457L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCalendarDocument calendarDocument = new QCalendarDocument("calendarDocument");

    public final chungbazi.chungbazi_be.global.entity.QBaseTimeEntity _super = new chungbazi.chungbazi_be.global.entity.QBaseTimeEntity(this);

    public final chungbazi.chungbazi_be.domain.cart.entity.QCart cart;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath document = createString("document");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isChecked = createBoolean("isChecked");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCalendarDocument(String variable) {
        this(CalendarDocument.class, forVariable(variable), INITS);
    }

    public QCalendarDocument(Path<? extends CalendarDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCalendarDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCalendarDocument(PathMetadata metadata, PathInits inits) {
        this(CalendarDocument.class, metadata, inits);
    }

    public QCalendarDocument(Class<? extends CalendarDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new chungbazi.chungbazi_be.domain.cart.entity.QCart(forProperty("cart"), inits.get("cart")) : null;
    }

}


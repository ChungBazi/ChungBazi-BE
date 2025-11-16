package chungbazi.chungbazi_be.domain.community.repository;

import chungbazi.chungbazi_be.domain.community.entity.Comment;
import chungbazi.chungbazi_be.domain.community.entity.ContentStatus;
import chungbazi.chungbazi_be.domain.community.entity.QComment;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findCommentsWithFilters(ContentStatus status, List<Long> excludedAuthorIds, List<Long> reportedCommentIds,
                                                 Long postId, Long lastCommentId, Pageable pageable){
        QComment comment = QComment.comment;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(comment.status.eq(status))
                .and(comment.post.id.eq(postId));

        if (!excludedAuthorIds.isEmpty()) {
            builder.and(comment.author.id.notIn(excludedAuthorIds));
        }
        if (!reportedCommentIds.isEmpty()) {
            builder.and(comment.id.notIn(reportedCommentIds));
        }
        if (lastCommentId != null && lastCommentId != 0) {
            builder.and(comment.id.gt(lastCommentId));
        }

        List<Comment> content = queryFactory
                .selectFrom(comment)
                .where(builder)
                .orderBy(comment.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(comment.count())
                .from(comment)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Long countCommentsWithFilters(Long postId, ContentStatus status, List<Long> excludedAuthorIds, List<Long> reportedCommentIds) {
        QComment comment = QComment.comment;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(comment.post.id.eq(postId))
                .and(comment.status.eq(status));

        if (!excludedAuthorIds.isEmpty()) {
            builder.and(comment.author.id.notIn(excludedAuthorIds));
        }
        if (!reportedCommentIds.isEmpty()) {
            builder.and(comment.id.notIn(reportedCommentIds));
        }

        return queryFactory
                .select(comment.count())
                .from(comment)
                .where(builder)
                .fetchOne();
    }
}

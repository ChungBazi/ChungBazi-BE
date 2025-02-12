package chungbazi.chungbazi_be.domain.document.repository;

import chungbazi.chungbazi_be.domain.document.entity.CalendarDocument;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarDocumentRepository extends JpaRepository<CalendarDocument, Long> {
    void deleteByIdIn(List<Long> deleteIds);

    List<CalendarDocument> findAllByCart_Id(Long cartId);
}

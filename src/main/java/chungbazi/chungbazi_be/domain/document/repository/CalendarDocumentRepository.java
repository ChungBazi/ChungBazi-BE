package chungbazi.chungbazi_be.domain.document.repository;

import chungbazi.chungbazi_be.domain.document.entity.CalendarDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarDocumentRepository extends JpaRepository<CalendarDocument, Long> {
}

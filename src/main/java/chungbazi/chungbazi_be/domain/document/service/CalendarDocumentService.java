package chungbazi.chungbazi_be.domain.document.service;

import chungbazi.chungbazi_be.domain.cart.dto.CartRequestDTO;
import chungbazi.chungbazi_be.domain.document.entity.CalendarDocument;
import chungbazi.chungbazi_be.domain.document.repository.CalendarDocumentRepository;
import chungbazi.chungbazi_be.global.apiPayload.code.status.ErrorStatus;
import chungbazi.chungbazi_be.global.apiPayload.exception.handler.NotFoundHandler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarDocumentService {

    private final CalendarDocumentRepository calendarDocumentRepository;

    // 서류 수정
    @Transactional
    public void editDocument(Long cartId, List<CartRequestDTO.CalendarDocument> dtos) {
        // dtos에 없는 거 삭제

        // dtos에 있는 거 내용 수정
    }

    // 서류 체크
    @Transactional
    public void checkDocument(Long cartId, Long documentId, boolean check) {

        CalendarDocument document = calendarDocumentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundHandler(
                        ErrorStatus.NOT_FOUND_DOCUMENT));

        document.updateCheck(check);
    }
}

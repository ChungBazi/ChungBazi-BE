package chungbazi.chungbazi_be.domain.document.service;

import chungbazi.chungbazi_be.domain.cart.dto.CartRequestDTO;
import chungbazi.chungbazi_be.domain.cart.entity.Cart;
import chungbazi.chungbazi_be.domain.cart.service.CartService;
import chungbazi.chungbazi_be.domain.document.dto.DocumentRequestDTO;
import chungbazi.chungbazi_be.domain.document.entity.CalendarDocument;
import chungbazi.chungbazi_be.domain.document.repository.CalendarDocumentRepository;
import chungbazi.chungbazi_be.domain.user.entity.User;
import chungbazi.chungbazi_be.domain.user.utils.UserHelper;
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
    private final UserHelper userHelper;
    private final CartService cartService;

    // 서류 생성
    @Transactional
    public void addDocument(DocumentRequestDTO.DocumentCreateList dto, Long cartId) {

        User user = userHelper.getAuthenticatedUser();
        Cart cart = cartService.findById(cartId);

        dto.getDocuments().forEach(doc -> {
            CalendarDocument document = new CalendarDocument(doc, cart);
            calendarDocumentRepository.save(document);
        });
    }

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

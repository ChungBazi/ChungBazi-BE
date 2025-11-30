package chungbazi.chungbazi_be.domain.document.controller;

import chungbazi.chungbazi_be.domain.document.dto.DocumentRequestDTO;
import chungbazi.chungbazi_be.domain.document.service.CalendarDocumentService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarDocumentController {

    private final CalendarDocumentService calendarDocumentService;

    //서류 생성
    @Operation(summary = "서류 추가 API", description = "서류 추가")
    @PostMapping("/{cartId}/documents")
    public ApiResponse<String> createDocuments(@RequestBody DocumentRequestDTO.DocumentCreateList createList,
                                               @PathVariable Long cartId) {

        calendarDocumentService.addDocument(createList, cartId);
        return ApiResponse.onSuccess("서류 생성이 완료되었습니다.");
    }

    //서류 체크
    @Operation(summary = "서류 체크 API", description = "서류 체크")
    @PutMapping("/{cartId}/documents/check")
    public ApiResponse<String> checkDocument(@PathVariable Long cartId,
                                             @RequestBody List<DocumentRequestDTO.DocumentCheck> checkList) {

        calendarDocumentService.checkDocument(cartId, checkList);
        return ApiResponse.onSuccess("서류가 체크 되었습니다.");
    }

    // 서류 수정 (내용 수정, 삭제)
    @Operation(summary = "서류 수정 API", description = "서류 수정")
    @PatchMapping("/{cartId}/documents")
    public ApiResponse<String> editDocument(@PathVariable Long cartId,
                                            @RequestBody List<DocumentRequestDTO.DocumentUpdate> dtos) {

        calendarDocumentService.editDocument(cartId, dtos);
        return ApiResponse.onSuccess("서류가 수정되었습니다.");
    }

}

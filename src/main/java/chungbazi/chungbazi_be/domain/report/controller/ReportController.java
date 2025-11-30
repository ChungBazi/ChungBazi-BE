package chungbazi.chungbazi_be.domain.report.controller;

import chungbazi.chungbazi_be.domain.report.dto.ReportRequest;
import chungbazi.chungbazi_be.domain.report.entity.enums.ReportType;
import chungbazi.chungbazi_be.domain.report.service.ReportService;
import chungbazi.chungbazi_be.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/posts/{postId}")
    @Operation(summary = "게시글 신고 api", description = "게시글을 신고하는 api입니다.")
    public ApiResponse<?> reportPost(@PathVariable Long postId, @RequestBody ReportRequest.ReportRequestDto requestDto) {
        reportService.reportPost(postId,requestDto);
        return ApiResponse.onSuccess("게시글이 신고되었습니다.");
    }

    @PostMapping("/comments/{commentId}")
    @Operation(summary = "댓글 신고 api", description = "댓글을 신고하는 api입니다.")
    public ApiResponse<?> reportComment(@PathVariable Long commentId, @RequestBody ReportRequest.ReportRequestDto requestDto) {
        reportService.reportComment(commentId,requestDto);
        return ApiResponse.onSuccess("댓글이 신고되었습니다.");
    }

    @PostMapping("/users/{userId}")
    @Operation(summary = "사용자 신고 api", description = "사용자를 신고하는 api입니다.")
    public ApiResponse<?> reportUser(@PathVariable Long userId, @RequestBody ReportRequest.ReportRequestDto requestDto) {
        reportService.reportUser(userId,requestDto);
        return ApiResponse.onSuccess("사용자가 신고되었습니다.");
    }

    @DeleteMapping("/{targetId}")
    @Operation(summary = "신고 삭제 api", description = """
            신고를 삭제하는 api입니다.
            게시글 신고를 취소하려면 targetId: 해당 postId, reportType: POST
            댓글 신고를 취소하려면 targetId: 해당 commentId, reportType: COMMENT
            유저 신고를 취소하려면 targetId: 해당 userId, reportType: USER
            """)
    public ApiResponse<?> reportDelete(@PathVariable Long targetId, @RequestParam ReportType reportType) {
        reportService.reportDelete(targetId,reportType);
        return ApiResponse.onSuccess("신고가 취소되었습니다.");
    }
}

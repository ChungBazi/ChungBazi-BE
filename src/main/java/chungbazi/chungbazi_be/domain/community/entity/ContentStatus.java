package chungbazi.chungbazi_be.domain.community.entity;

public enum ContentStatus {
    VISIBLE,    // 기본 공개 상태
    HIDDEN,     // 신고 임계치 도달 → 자동 숨김 상태
    DELETED     // 운영자 승인하여 삭제된 상태
}

package chungbazi.chungbazi_be.domain.document.dto;

import lombok.Getter;

public class DocumentResponseDTO {

    @Getter
    public static class DocumentDTO {
        private Long documentId;
        private String content;
        private boolean checked;

        public DocumentDTO(Long documentId, String content, boolean checked) {
            this.documentId = documentId;
            this.content = content;
            this.checked = checked;
        }
    }
}

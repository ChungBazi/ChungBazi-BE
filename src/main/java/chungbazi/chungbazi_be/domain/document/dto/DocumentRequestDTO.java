package chungbazi.chungbazi_be.domain.document.dto;

import java.util.List;
import lombok.Getter;

public class DocumentRequestDTO {


    @Getter
    public static class DocumentCreateList {
        private List<String> documents;
    }
}

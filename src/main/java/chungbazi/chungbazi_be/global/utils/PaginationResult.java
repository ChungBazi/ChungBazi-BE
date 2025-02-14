package chungbazi.chungbazi_be.global.utils;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaginationResult<T> {
    private List<T> items;
    private Long nextCursor;
    private boolean hasNext;
}

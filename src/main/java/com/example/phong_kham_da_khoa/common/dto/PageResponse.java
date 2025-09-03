package com.example.phong_kham_da_khoa.common.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

/**
 * Gói thông tin phân trang gọn gàng cho FE.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content;
    private int page;             // 0-based
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((totalElements * 1.0) / Math.max(size, 1));
        boolean last = (page + 1) >= totalPages;
        return PageResponse.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(last)
                .build();
    }

    /** Map trực tiếp từ Spring Page<E> sang PageResponse<T> qua mapper. */
    public static <E, T> PageResponse<T> from(Page<E> page, Function<E, T> mapper) {
        List<T> mapped = page.getContent().stream().map(mapper).toList();
        return PageResponse.<T>builder()
                .content(mapped)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    /** Nếu đã là Page<T> rồi thì dùng hàm này. */
    public static <T> PageResponse<T> from(Page<T> page) {
        return from(page, Function.identity());
    }
}

package com.eskcti.comment_service.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageOutput {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<CommentOutput> content;
}

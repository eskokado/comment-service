package com.eskcti.comment_service.api.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentOutput {
    private String id;
    private String text;
    private String author;
    private Instant createdAt;
}

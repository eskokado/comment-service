package com.eskcti.comment_service.api.model;

import lombok.Data;

@Data
public class ModerationInput {
    private String text;
    private String commentId;
}

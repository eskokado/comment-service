package com.eskcti.comment_service.api.controller;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eskcti.comment_service.api.client.ModerationClient;
import com.eskcti.comment_service.api.model.CommentInput;
import com.eskcti.comment_service.api.model.CommentOutput;
import com.eskcti.comment_service.api.model.CommentPageOutput;
import com.eskcti.comment_service.api.model.CommentRejectedOutput;
import com.eskcti.comment_service.api.model.ModerationInput;
import com.eskcti.comment_service.api.model.ModerationOutput;
import com.eskcti.comment_service.domain.model.Comment;
import com.eskcti.comment_service.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentRepository commentRepository;
    private final ModerationClient moderationClient;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CommentInput input) {
        UUID commentId = UUID.randomUUID();
        log.info("Creating comment {} for author {}", commentId, input.getAuthor());

        ModerationInput moderationInput = new ModerationInput();
        moderationInput.setText(input.getText());
        moderationInput.setCommentId(commentId.toString());

        ModerationOutput moderationResult = moderationClient.moderate(moderationInput);

        if (!moderationResult.isApproved()) {
            log.info("Comment {} rejected by moderation: {}", commentId, moderationResult.getReason());
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new CommentRejectedOutput(moderationResult.getReason()));
        }

        Comment comment = Comment.builder()
                .id(commentId)
                .text(input.getText())
                .author(input.getAuthor())
                .createdAt(Instant.now())
                .build();

        commentRepository.saveAndFlush(comment);
        log.info("Comment {} approved and stored", commentId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToOutput(comment));
    }

    @GetMapping("/{id}")
    public CommentOutput getById(@PathVariable("id") UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        return convertToOutput(comment);
    }

    @GetMapping
    public CommentPageOutput getAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);

        return CommentPageOutput.builder()
                .page(comments.getNumber())
                .size(comments.getSize())
                .totalElements(comments.getTotalElements())
                .totalPages(comments.getTotalPages())
                .content(comments.map(this::convertToOutput).getContent())
                .build();
    }

    private CommentOutput convertToOutput(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId().toString())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

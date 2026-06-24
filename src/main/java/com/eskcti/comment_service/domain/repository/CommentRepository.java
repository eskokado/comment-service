package com.eskcti.comment_service.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eskcti.comment_service.domain.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID>{
    Page<Comment> findAll(Pageable pageable);
}

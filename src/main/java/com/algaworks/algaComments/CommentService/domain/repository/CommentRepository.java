package com.algaworks.algaComments.CommentService.domain.repository;

import com.algaworks.algaComments.CommentService.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}

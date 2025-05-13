package com.algaworks.algaComments.CommentService.domain.service;

import com.algaworks.algaComments.CommentService.api.client.ModerationClient;
import com.algaworks.algaComments.CommentService.api.model.CommentInput;
import com.algaworks.algaComments.CommentService.api.model.CommentOutput;
import com.algaworks.algaComments.CommentService.api.model.ModerationRequest;
import com.algaworks.algaComments.CommentService.api.model.ModerationResponse;
import com.algaworks.algaComments.CommentService.domain.exception.CommentNotFoundException;
import com.algaworks.algaComments.CommentService.domain.exception.ModerationRejectedException;
import com.algaworks.algaComments.CommentService.domain.model.Comment;
import com.algaworks.algaComments.CommentService.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModerationClient moderationClient;

    public CommentOutput createComment(CommentInput input) {

        Comment comment = Comment.builder()
                .text(input.getText())
                .author(input.getAuthor())
                .build();

        ModerationRequest request = new ModerationRequest(comment.getId(), comment.getText());
        ModerationResponse response = moderationClient.moderateComment(request);

        if (!response.isApproved()) {
            throw new ModerationRejectedException(response.getReason());
        }

        Comment savedComment = commentRepository.saveAndFlush(comment);
        return mapToOutPut(savedComment);
    }

    public CommentOutput getCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        return mapToOutPut(comment);
    }

    public Page<CommentOutput> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(this::mapToOutPut);
    }

    private CommentOutput mapToOutPut(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();

    }

}

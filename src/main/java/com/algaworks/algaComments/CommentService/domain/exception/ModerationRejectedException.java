package com.algaworks.algaComments.CommentService.domain.exception;

public class ModerationRejectedException extends RuntimeException {
    public ModerationRejectedException(String message) {
        super(message);
    }
}

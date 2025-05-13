package com.algaworks.algaComments.CommentService.api.client;

import com.algaworks.algaComments.CommentService.api.model.ModerationRequest;
import com.algaworks.algaComments.CommentService.api.model.ModerationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ModerationClient {

    @PostExchange("/api/moderate")
    ModerationResponse moderateComment(@RequestBody ModerationRequest request);
}

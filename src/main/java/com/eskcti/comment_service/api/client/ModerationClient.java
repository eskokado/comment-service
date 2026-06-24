package com.eskcti.comment_service.api.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.eskcti.comment_service.api.model.ModerationInput;
import com.eskcti.comment_service.api.model.ModerationOutput;

@HttpExchange("/api/moderate")
public interface ModerationClient {

    @PostExchange
    ModerationOutput moderate(@RequestBody ModerationInput input);
}

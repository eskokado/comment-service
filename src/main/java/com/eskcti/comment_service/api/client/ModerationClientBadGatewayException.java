package com.eskcti.comment_service.api.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class ModerationClientBadGatewayException extends RuntimeException {

    public ModerationClientBadGatewayException(String message) {
        super(message);
    }

}

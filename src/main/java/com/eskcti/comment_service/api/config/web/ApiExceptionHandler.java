package com.eskcti.comment_service.api.config.web;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eskcti.comment_service.api.client.ModerationClientBadGatewayException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{


    @ExceptionHandler(ResourceAccessException.class)
    public ProblemDetail handleResourceAccess(ResourceAccessException exception) {
        if (exception.getCause() instanceof SocketTimeoutException timeoutException) {
            return handleTimeout(timeoutException);
        }

        if (exception.getCause() instanceof ConnectException connectException) {
            return handleTimeout(connectException);
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setTitle("Bad gateway");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;
    }

    @ExceptionHandler({
        SocketTimeoutException.class,
        ConnectException.class,
        ClosedChannelException.class
    })
    public ProblemDetail handleTimeout(IOException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problemDetail.setTitle("Gateway timeout");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }

    @ExceptionHandler(ModerationClientBadGatewayException.class)
    public ProblemDetail handleBadGateway(ModerationClientBadGatewayException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setTitle("Bad gateway");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;
    }
}

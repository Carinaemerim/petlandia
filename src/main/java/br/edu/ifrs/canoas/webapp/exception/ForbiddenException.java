package br.edu.ifrs.canoas.webapp.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
@AllArgsConstructor
public class ForbiddenException extends RuntimeException {
    String message;
}

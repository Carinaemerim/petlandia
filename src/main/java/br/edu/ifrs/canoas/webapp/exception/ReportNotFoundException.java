package br.edu.ifrs.canoas.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "report not found")
public class ReportNotFoundException extends RuntimeException {
}
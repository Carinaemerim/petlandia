package br.edu.ifrs.canoas.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "announce not found")
public class AnnounceNotFoundException extends RuntimeException { }
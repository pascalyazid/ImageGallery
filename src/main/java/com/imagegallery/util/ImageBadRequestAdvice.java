package com.imagegallery.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ImageBadRequestAdvice {

    @ResponseBody
    @ExceptionHandler(ImageBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String imageBadRequest(ImageBadRequestException ex) {
        return ex.getMessage();
    }
}

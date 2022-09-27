package com.imagegallery.util;

public class ImageBadRequestException extends RuntimeException {

    public ImageBadRequestException(String uuid) {
        super("Bad Request with Image " + uuid);
    }

}

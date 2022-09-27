package com.imagegallery.util;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String uuid) {
        super("Could not find Image " + uuid);
    }
}

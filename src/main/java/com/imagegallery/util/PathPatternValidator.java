package com.imagegallery.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PathPatternValidator implements
        ConstraintValidator<PathPatternConstraint, String> {

    @Override
    public void initialize(PathPatternConstraint path) {
    }

    @Override
    public boolean isValid(String path, ConstraintValidatorContext constraintValidatorContext) {
        return path != null && path.matches("[^\\s]+(\\.(?i)(jpg|png|gif|bmp))$")
                && (path.length() > 5) && (path.length() < 99);
    }
}

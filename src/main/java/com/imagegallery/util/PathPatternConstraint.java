package com.imagegallery.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PathPatternValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PathPatternConstraint {
    String message() default "Invalid Image: .jpg, .png, .gif, .bmp";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


package com.enigma.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AllowedCheckInDateValidator.class})
@Documented
public @interface AllowedCheckInDate {
    String message() default "CheckIn must be 1 day ahead of arrival and up to 1 month in advance";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

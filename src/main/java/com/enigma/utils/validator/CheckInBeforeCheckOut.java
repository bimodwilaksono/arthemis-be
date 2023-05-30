package com.enigma.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CheckInBeforeCheckOutValidator.class})
@Documented
public @interface CheckInBeforeCheckOut {
    String message() default "CheckIn date must be before CheckOut date";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.enigma.utils.validator;

import com.enigma.model.Order;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckInBeforeCheckOutValidator implements ConstraintValidator<CheckInBeforeCheckOut, Order> {
    @Override
    public void initialize(CheckInBeforeCheckOut constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Order order, ConstraintValidatorContext constraintValidatorContext) {
        if(order == null){
            return false;
        }
        return order.getCheckInDate().isBefore(order.getCheckOutDate());
    }
}

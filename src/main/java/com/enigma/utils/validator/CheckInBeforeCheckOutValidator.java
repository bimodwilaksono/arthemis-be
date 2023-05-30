package com.enigma.utils.validator;

import com.enigma.model.request.OrderRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CheckInBeforeCheckOutValidator implements ConstraintValidator<CheckInBeforeCheckOut, OrderRequest> {
    @Override
    public void initialize(CheckInBeforeCheckOut constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OrderRequest order, ConstraintValidatorContext constraintValidatorContext) {
        if(order == null){
            return false;
        }
        return order.getCheckInDate().isBefore(order.getCheckOutDate());
    }
}

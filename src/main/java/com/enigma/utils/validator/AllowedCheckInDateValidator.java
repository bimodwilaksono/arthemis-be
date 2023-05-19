package com.enigma.utils.validator;

import com.enigma.model.Order;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import static com.enigma.utils.constants.OrderConstant.MAX_MONTHS_AHEAD_OF_ARRIVAL;
import static com.enigma.utils.constants.OrderConstant.MIN_DAYS_AHEAD_OF_ARRIVAL;


public class AllowedCheckInDateValidator implements ConstraintValidator<AllowedCheckInDate, Order> {
    @Override
    public void initialize(AllowedCheckInDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Order order, ConstraintValidatorContext constraintValidatorContext) {
        if(order == null){
            return false;
        }
        return LocalDate.now().isBefore(order.getCheckInDate())
                && order.getCheckInDate().isBefore(LocalDate.now().plusMonths(MAX_MONTHS_AHEAD_OF_ARRIVAL)
                .plusDays(MIN_DAYS_AHEAD_OF_ARRIVAL));
    }
}

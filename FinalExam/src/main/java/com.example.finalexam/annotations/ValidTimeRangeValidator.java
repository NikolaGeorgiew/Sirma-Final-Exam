package com.example.finalexam.annotations;

import com.example.finalexam.model.MatchRecord;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTimeRangeValidator implements ConstraintValidator<ValidTimeRange, MatchRecord> {

    @Override
    public boolean isValid(MatchRecord value, ConstraintValidatorContext context) {
        if (value.getFromMinutes() == null || value.getToMinutes() == null) {
            return true; // Null values are handled by @NotNull
        }
        //Check that fromMinutes is less than or equal to toMinutes
        return value.getToMinutes() >= value.getFromMinutes();
    }
}

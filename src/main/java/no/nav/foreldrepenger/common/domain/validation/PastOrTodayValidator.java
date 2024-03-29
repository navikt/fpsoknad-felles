package no.nav.foreldrepenger.common.domain.validation;

import static java.time.LocalDate.now;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

public class PastOrTodayValidator implements ConstraintValidator<PastOrToday, LocalDate> {

    private boolean nullable;

    @Override
    public boolean isValid(LocalDate dato, ConstraintValidatorContext context) {
        if (dato == null && nullable) {
            return true;
        }
        return dato != null && (dato.equals(now()) || dato.isBefore(now()));
    }

    @Override
    public void initialize(PastOrToday constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
    }
}

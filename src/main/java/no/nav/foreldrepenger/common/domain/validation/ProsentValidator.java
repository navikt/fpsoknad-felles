package no.nav.foreldrepenger.common.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.validation.annotations.Prosent;

public class ProsentValidator implements ConstraintValidator<Prosent, Double> {

    private double min;
    private double max;

    @Override
    public void initialize(Prosent prosent) {
        this.min = prosent.min();
        this.max = prosent.max();
    }

    @Override
    public boolean isValid(Double prosent, ConstraintValidatorContext context) {
        return !(prosent == null || prosent.doubleValue() < min || prosent.doubleValue() > max);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [min=" + min + ", max=" + max + "]";
    }
}

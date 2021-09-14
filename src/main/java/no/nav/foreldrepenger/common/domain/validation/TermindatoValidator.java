package no.nav.foreldrepenger.common.domain.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.validation.annotations.Termindato;

public class TermindatoValidator implements ConstraintValidator<Termindato, LocalDate> {

    private int weeks;

    @Override
    public void initialize(Termindato annotation) {
        weeks = annotation.weeks();
    }

    @Override
    public boolean isValid(LocalDate terminDato, ConstraintValidatorContext context) {
        return terminDato.isAfter(LocalDate.now().minusWeeks(weeks).minusDays(1));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [weeks=" + weeks + "]";
    }
}

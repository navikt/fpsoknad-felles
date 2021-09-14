package no.nav.foreldrepenger.common.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.felles.LukketPeriode;
import no.nav.foreldrepenger.common.domain.validation.annotations.Periode;

public class PeriodeValidator implements ConstraintValidator<Periode, LukketPeriode> {

    @Override
    public boolean isValid(LukketPeriode periode, ConstraintValidatorContext context) {
        return periode.fom().isBefore(periode.tom());
    }

}

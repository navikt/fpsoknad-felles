package no.nav.foreldrepenger.common.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.dto.felles.LukketPeriode;
import no.nav.foreldrepenger.common.dto.validation.annotations.Periode;

public class PeriodeValidator implements ConstraintValidator<Periode, LukketPeriode> {

    @Override
    public boolean isValid(LukketPeriode periode, ConstraintValidatorContext context) {
        return periode.fom().isBefore(periode.tom());
    }

}

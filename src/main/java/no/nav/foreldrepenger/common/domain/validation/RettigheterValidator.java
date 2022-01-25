package no.nav.foreldrepenger.common.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.foreldrepenger.Rettigheter;
import no.nav.foreldrepenger.common.domain.validation.annotations.Rettighet;

public class RettigheterValidator implements ConstraintValidator<Rettighet, Rettigheter> {

    @Override
    public boolean isValid(Rettigheter rettigheter, ConstraintValidatorContext ctx) {
        if (rettigheter.harAleneOmsorgForBarnet() && rettigheter.harAnnenForelderRett()) {
            return false;
        }
        return true;
    }
}

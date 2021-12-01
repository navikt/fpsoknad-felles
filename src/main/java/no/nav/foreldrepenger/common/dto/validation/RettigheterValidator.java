package no.nav.foreldrepenger.common.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.dto.foreldrepenger.Rettigheter;
import no.nav.foreldrepenger.common.dto.validation.annotations.Rettighet;

public class RettigheterValidator implements ConstraintValidator<Rettighet, Rettigheter> {

    @Override
    public boolean isValid(Rettigheter rettigheter, ConstraintValidatorContext ctx) {
        if (!rettigheter.isHarAleneOmsorgForBarnet() && rettigheter.getDatoForAleneomsorg() != null) {
            return false;
        }
        return true;
    }
}

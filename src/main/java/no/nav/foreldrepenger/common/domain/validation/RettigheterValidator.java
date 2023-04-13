package no.nav.foreldrepenger.common.domain.validation;

import static no.nav.foreldrepenger.common.util.LangUtil.toBoolean;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Rettigheter;
import no.nav.foreldrepenger.common.domain.validation.annotations.Rettighet;


public class RettigheterValidator implements ConstraintValidator<Rettighet, Rettigheter> {

    @Override
    public boolean isValid(Rettigheter rettigheter, ConstraintValidatorContext ctx) {
        return !toBoolean(rettigheter.harAleneOmsorgForBarnet()) ||
                (!rettigheter.harAnnenForelderRett() && !toBoolean(rettigheter.harMorUføretrygd()));
    }
}

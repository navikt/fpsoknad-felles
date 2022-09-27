package no.nav.foreldrepenger.common.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.foreldrepenger.Rettigheter;
import no.nav.foreldrepenger.common.domain.validation.annotations.Rettighet;

import static no.nav.foreldrepenger.common.util.LangUtil.toBoolean;


public class RettigheterValidator implements ConstraintValidator<Rettighet, Rettigheter> {

    @Override
    public boolean isValid(Rettigheter rettigheter, ConstraintValidatorContext ctx) {
        if (toBoolean(rettigheter.harAleneOmsorgForBarnet()) && (rettigheter.harAnnenForelderRett() || toBoolean(rettigheter.harMorUføretrygd()))) {
            return false;
        }
        return true;
    }
}

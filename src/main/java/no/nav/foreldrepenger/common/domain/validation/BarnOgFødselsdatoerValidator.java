package no.nav.foreldrepenger.common.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.validation.annotations.BarnOgFødselsdatoer;

public class BarnOgFødselsdatoerValidator implements ConstraintValidator<BarnOgFødselsdatoer, Fødsel> {

    @Override
    public boolean isValid(Fødsel fødsel, ConstraintValidatorContext ctx) {
        return fødsel.getFødselsdato().size() == 1;
    }
}

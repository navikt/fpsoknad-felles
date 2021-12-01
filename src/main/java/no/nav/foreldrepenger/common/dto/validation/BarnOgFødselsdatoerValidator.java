package no.nav.foreldrepenger.common.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.nav.foreldrepenger.common.dto.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.dto.validation.annotations.BarnOgFødselsdatoer;

public class BarnOgFødselsdatoerValidator implements ConstraintValidator<BarnOgFødselsdatoer, Fødsel> {

    @Override
    public boolean isValid(Fødsel fødsel, ConstraintValidatorContext ctx) {
        return fødsel.getFødselsdato().size() == 1;
    }
}

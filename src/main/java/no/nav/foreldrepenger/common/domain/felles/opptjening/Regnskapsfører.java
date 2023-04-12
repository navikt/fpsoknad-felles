package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public record Regnskapsfører(@Length(max = 100) @Pattern(regexp = FRITEKST) String navn, @Pattern(regexp = FRITEKST) String telefon) {

    @Override
    public String toString() {
        return "Regnskapsfører{" + "navn='" + mask(navn) + '\'' + ", telefon='" + mask(telefon) + '\'' + '}';
    }
}

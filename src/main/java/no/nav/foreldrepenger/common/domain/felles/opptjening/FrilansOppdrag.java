package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record FrilansOppdrag(@Length(max = 100) @Pattern(regexp = FRITEKST) String oppdragsgiver, @Valid ÅpenPeriode periode) {

    @Override
    public String toString() {
        return "FrilansOppdrag{" + "oppdragsgiver='" + mask(oppdragsgiver) + '\'' + ", periode=" + periode + '}';
    }
}

package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record FrilansOppdrag(@Length(max = 100) @Pattern(regexp = FRITEKST) String oppdragsgiver, @Valid ÅpenPeriode periode) {

}

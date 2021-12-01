package no.nav.foreldrepenger.common.dto.felles.opptjening;

import org.hibernate.validator.constraints.Length;

import no.nav.foreldrepenger.common.dto.felles.ÅpenPeriode;

public record FrilansOppdrag(@Length(max = 100) String oppdragsgiver, ÅpenPeriode periode) {

}

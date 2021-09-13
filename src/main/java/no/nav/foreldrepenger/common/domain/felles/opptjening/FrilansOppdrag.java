package no.nav.foreldrepenger.common.domain.felles.opptjening;

import org.hibernate.validator.constraints.Length;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record FrilansOppdrag(@Length(max = 100) String oppdragsgiver, ÅpenPeriode periode) {

}

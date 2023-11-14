package no.nav.foreldrepenger.common.domain.felles.opptjening;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record AnnenOpptjening(AnnenOpptjeningType type, @Valid ÅpenPeriode periode) {
}

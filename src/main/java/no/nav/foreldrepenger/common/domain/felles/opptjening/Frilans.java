package no.nav.foreldrepenger.common.domain.felles.opptjening;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

import jakarta.validation.Valid;

public record Frilans(@Valid ÅpenPeriode periode,
                      boolean jobberFremdelesSomFrilans) {
}

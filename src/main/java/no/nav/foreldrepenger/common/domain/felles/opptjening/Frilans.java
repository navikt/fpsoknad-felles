package no.nav.foreldrepenger.common.domain.felles.opptjening;

import java.util.List;

import javax.validation.Valid;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

public record Frilans(@Valid ÅpenPeriode periode, boolean harInntektFraFosterhjem, boolean nyOppstartet,
                      boolean jobberFremdelesSomFrilans, @Valid List<FrilansOppdrag> frilansOppdrag) {
}

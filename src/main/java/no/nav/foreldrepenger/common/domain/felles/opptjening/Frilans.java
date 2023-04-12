package no.nav.foreldrepenger.common.domain.felles.opptjening;

import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;

import jakarta.validation.Valid;
import java.util.List;

public record Frilans(@Valid ÅpenPeriode periode,
                      boolean harInntektFraFosterhjem,
                      boolean nyOppstartet,
                      boolean jobberFremdelesSomFrilans,
                      @Valid List<FrilansOppdrag> frilansOppdrag) {
}

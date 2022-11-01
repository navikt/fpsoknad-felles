package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.List;

public record FpÅpenBehandling(BehandlingTilstand tilstand, List<UttakPeriode> søknadsperioder) { }

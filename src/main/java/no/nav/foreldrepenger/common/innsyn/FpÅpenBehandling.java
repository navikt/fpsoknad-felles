package no.nav.foreldrepenger.common.innsyn;

import java.util.List;

public record FpÅpenBehandling(BehandlingTilstand tilstand, List<UttakPeriode> søknadsperioder) { }

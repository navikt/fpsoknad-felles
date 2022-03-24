package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

public record FpÅpenBehandling(BehandlingTilstand tilstand, Set<Søknadsperiode> søknadsperioder) { }

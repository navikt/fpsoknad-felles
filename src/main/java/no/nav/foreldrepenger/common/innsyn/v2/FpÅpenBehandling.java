package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

record FpÅpenBehandling(BehandlingTilstand tilstand,
                        Set<Søknadsperiode> søknadsperioder) { }

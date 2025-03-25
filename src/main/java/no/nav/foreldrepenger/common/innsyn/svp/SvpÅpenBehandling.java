package no.nav.foreldrepenger.common.innsyn.svp;

import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.innsyn.BehandlingTilstand;

public record SvpÅpenBehandling(@NotNull BehandlingTilstand tilstand, @NotNull Søknad søknad) {
}

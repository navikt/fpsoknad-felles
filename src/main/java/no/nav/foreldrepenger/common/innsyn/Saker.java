package no.nav.foreldrepenger.common.innsyn;

import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.innsyn.svp.SvpSak;

import java.util.Set;

public record Saker(@NotNull Set<FpSak> foreldrepenger,
                    @NotNull Set<EsSak> engangsstønad,
                    @NotNull Set<SvpSak> svangerskapspenger) {
}

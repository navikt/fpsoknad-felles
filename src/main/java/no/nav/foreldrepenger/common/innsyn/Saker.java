package no.nav.foreldrepenger.common.innsyn;

import no.nav.foreldrepenger.common.innsyn.svp.SvpSak;

import java.util.Set;

public record Saker(Set<FpSak> foreldrepenger,
             Set<EsSak> engangsstønad,
             Set<SvpSak> svangerskapspenger) {
}

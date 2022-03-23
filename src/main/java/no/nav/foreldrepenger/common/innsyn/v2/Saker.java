package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

public record Saker(Set<FpSak> foreldrepenger,
             Set<EsSak> engangsstønad,
             Set<SvpSak> svangerskapspenger) {
}

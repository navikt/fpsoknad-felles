package no.nav.foreldrepenger.common.innsyn.svp;

import java.util.Set;

import no.nav.foreldrepenger.common.innsyn.Aktivitet;

public record Tilrettelegging(Aktivitet aktivitet, Set<TilretteleggingPeriode> perioder) {
}

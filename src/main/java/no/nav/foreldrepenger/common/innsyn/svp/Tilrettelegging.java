package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;
import java.util.Set;

import no.nav.foreldrepenger.common.innsyn.Aktivitet;

public record Tilrettelegging(Aktivitet aktivitet,
                              LocalDate behovFrom,
                              String risikofaktorer,
                              String tiltak,
                              Set<TilretteleggingPeriode> perioder) {
}

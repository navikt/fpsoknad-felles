package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;
import java.util.Set;

import no.nav.foreldrepenger.common.innsyn.Aktivitet;

public record AktivitetVedtak(Aktivitet aktivitet,
                              LocalDate tilretteleggingBehovFom,
                              Set<SvpPeriode> svpPerioder,
                              Set<OppholdPeriode> oppholdPerioder,
                              Avslutning avslutning) {
}

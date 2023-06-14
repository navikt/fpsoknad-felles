package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;
import java.util.Set;

import no.nav.foreldrepenger.common.innsyn.Aktivitet;

public record AktivitetVedtak(Aktivitet aktivitet,
                              LocalDate tilretteleggingBehovFom,
                              Set<Periode> perioder,
                              AvslutningÅrsak avslutningÅrsak) {

    public enum AvslutningÅrsak {
        NORMAL,
        TILBAKE_I_HEL_STILLING,
        AVSLAG_OVERGANG_FORELDREPENGER,
        AVSLAG_FØDSEL,
        AVSLAG_ANNET,
    }
}

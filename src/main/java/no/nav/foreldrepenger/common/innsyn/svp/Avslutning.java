package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDate;

public record Avslutning(LocalDate dato, Årsak årsak) {
    public enum Årsak {
        TILBAKE_I_HEL_STILLING,
        AVSLAG_OVERGANG_FORELDREPENGER,
        AVSLAG_FØDSEL,
        AVSLAG_ANNET,
        //TODO utvide med flere
    }
}


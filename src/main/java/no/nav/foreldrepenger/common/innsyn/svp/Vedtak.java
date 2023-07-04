package no.nav.foreldrepenger.common.innsyn.svp;

import java.util.Set;


public record Vedtak(Set<Arbeidsforhold> arbeidsforhold, AvslagÅrsak avslagÅrsak) {

    public enum AvslagÅrsak {
        ARBEIDSGIVER_KAN_TILRETTELEGGE,
        SØKER_ER_INNVILGET_SYKEPENGER,
        MANGLENDE_DOKUMENTASJON,
        ANNET,
    }
}

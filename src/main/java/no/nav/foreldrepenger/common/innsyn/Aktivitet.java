package no.nav.foreldrepenger.common.innsyn;

import jakarta.validation.constraints.NotNull;

public record Aktivitet(@NotNull Type type, Arbeidsgiver arbeidsgiver, String arbeidsgiverNavn) {

    public enum Type {
        FRILANS, ORDINÆRT_ARBEID, SELVSTENDIG_NÆRINGSDRIVENDE, ANNET
    }
}

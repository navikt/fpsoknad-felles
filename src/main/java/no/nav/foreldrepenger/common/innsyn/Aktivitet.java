package no.nav.foreldrepenger.common.innsyn;

public record Aktivitet(Type type, Arbeidsgiver arbeidsgiver, String arbeidsgiverNavn) {

    public enum Type {
        FRILANS, ORDINÆRT_ARBEID, SELVSTENDIG_NÆRINGSDRIVENDE, ANNET
    }
}

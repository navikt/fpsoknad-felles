package no.nav.foreldrepenger.common.innsyn.v2;

public record Aktivitet(Type type, Arbeidsgiver arbeidsgiver) {

    public enum Type {
        FRILANS, ORDINÆRT_ARBEID, SELVSTENDIG_NÆRINGSDRIVENDE, ANNET
    }
}

package no.nav.foreldrepenger.common.innsyn;

public enum DekningsgradSak {
    ÅTTI, HUNDRE;

    public static DekningsgradSak valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        if (value == 80) {
            return ÅTTI;
        }
        if (value == 100) {
            return HUNDRE;
        }
        throw new IllegalArgumentException("Ukjent dekningsgrad " + value);
    }
}

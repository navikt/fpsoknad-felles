package no.nav.foreldrepenger.common.innsyn;

import static no.nav.foreldrepenger.common.util.StringUtil.mask;

public record Arbeidsgiver(String id, ArbeidsgiverType type) {

    public enum ArbeidsgiverType {
        PRIVAT,
        ORGANISASJON
    }

    @Override
    public String toString() {
        return "Arbeidsgiver{" + "id='" + mask(id) + '\'' + ", type=" + type + '}';
    }
}

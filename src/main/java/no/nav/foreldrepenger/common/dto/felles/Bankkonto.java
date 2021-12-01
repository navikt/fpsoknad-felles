package no.nav.foreldrepenger.common.dto.felles;

import static no.nav.foreldrepenger.common.util.StringUtil.mask;

public record Bankkonto(String kontonummer, String banknavn) {

    public static final Bankkonto UKJENT = new Bankkonto("", "");

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [kontonummer=" + mask(kontonummer) + ", banknavn=" + banknavn + "]";
    }
}

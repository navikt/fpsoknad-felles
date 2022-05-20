package no.nav.foreldrepenger.common.util;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.FagsakType.SVANGERSKAPSPENGER;

import java.util.List;

import no.nav.foreldrepenger.common.innsending.SøknadType;

public enum Versjon {
    V1("urn:no:nav:vedtak:felles:xml:soeknad:svangerskapspenger:v1"),
    V3("urn:no:nav:vedtak:felles:xml:soeknad:foreldrepenger:v3",
            "urn:no:nav:vedtak:felles:xml:soeknad:engangsstoenad:v3",
            "urn:no:nav:vedtak:felles:xml:soeknad:endringssoeknad:v3"),
    UKJENT;

    public static final Versjon DEFAULT_VERSJON = V3;
    public static final Versjon DEFAULT_SVP_VERSJON = V1;

    private final List<String> namespaces;

    Versjon() {
        this(emptyList());
    }

    Versjon(String... namespaces) {
        this(asList(namespaces));
    }

    Versjon(List<String> namespaces) {
        this.namespaces = namespaces;
    }

    public static Versjon defaultVersjon(SøknadType type) {
        return SVANGERSKAPSPENGER.equals(type.fagsakType()) ? DEFAULT_SVP_VERSJON : DEFAULT_VERSJON;
    }
}

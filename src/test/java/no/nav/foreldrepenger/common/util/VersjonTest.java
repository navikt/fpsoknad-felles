package no.nav.foreldrepenger.common.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class VersjonTest {

    @Test
    void kanHenteNamespaceFraMap() {
        var versjon = Versjon.namespaceFra("urn:no:nav:vedtak:felles:xml:soeknad:svangerskapspenger:v1");
        assertThat(versjon.erUkjent()).isFalse();
    }
}

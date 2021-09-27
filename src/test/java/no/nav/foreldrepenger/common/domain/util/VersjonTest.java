package no.nav.foreldrepenger.common.domain.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.util.Versjon;

class VersjonTest {

    @Test
    void kanHenteNamespaceFraMap() {
        var versjon = Versjon.namespaceFra("urn:no:nav:vedtak:felles:xml:soeknad:svangerskapspenger:v1");
        assertThat(versjon.erUkjent()).isFalse();
    }
}

package no.nav.foreldrepenger.common.domain.felles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class NavnOrgTest extends SerializationTestBase {

    @Test
    void navnEquals() {
        var n = new Navn("Ole", "Mellomnavn", "Skrivepult");
        var n1 = new Navn("Ole", "Mellomnavn", "Skrivepult");
        assertEquals(n, n1);
    }

    @Test
    void testNavnFormatteres() {
        var enkeltNavn = new Navn("FORNAVN", "MELLOMNAVN", "ETTERNAVN");
        assertThat(enkeltNavn.navn()).isEqualTo("Fornavn Mellomnavn Etternavn");
        assertThat(enkeltNavn.fornavn()).isEqualTo("Fornavn");
        assertThat(enkeltNavn.etternavn()).isEqualTo("Etternavn");
    }
}

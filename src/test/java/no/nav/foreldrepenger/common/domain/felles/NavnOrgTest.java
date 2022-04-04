package no.nav.foreldrepenger.common.domain.felles;

import static no.nav.foreldrepenger.common.domain.Orgnummer.MAGIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class NavnOrgTest extends SerializationTestBase {

    @Test
    void navnEquals() {
        var n = new Navn("Ole", "Mellomnavn", "Skrivepult");
        var n1 = new Navn("Ole", "Mellomnavn", "Skrivepult");
        assertEquals(n, n1);
    }

    @Test
    void orgOK() {
        assertNotNull(new Orgnummer("999999999"));
        assertThrows(IllegalArgumentException.class, () -> new Orgnummer("123"));
        assertNotNull(MAGIC);
    }
}

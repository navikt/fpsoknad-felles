package no.nav.foreldrepenger.common.domain.domain.svangerskapspenger;

import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.delvisTilrettelegging;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.svp;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.domain.SerializationTestBase;

class TestSvangerskapsepengerSerialization extends SerializationTestBase {

    @Test
    void testSVP() {
        test(svp(), false);
    }

    @Test
    void testTilrettelegging() {
        test(delvisTilrettelegging(), false);
    }


}

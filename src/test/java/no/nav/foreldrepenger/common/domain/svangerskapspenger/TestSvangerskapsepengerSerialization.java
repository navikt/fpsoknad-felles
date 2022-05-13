package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.delvisTilrettelegging;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.svp;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.util.SerializationTestBase;

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

package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.delTilrettelegging;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.svp;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.tilretteleggingbehovPrivat;

import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.util.SerializationTestBase;

class TestSvangerskapsepengerSerialization extends SerializationTestBase {

    @Test
    void testSVP() {
        test(svp(), false);
    }

    @Test
    void roundtripTestDelvisTilrettelegging() {
        test(tilretteleggingbehovPrivat(List.of(delTilrettelegging())), true);
    }


}

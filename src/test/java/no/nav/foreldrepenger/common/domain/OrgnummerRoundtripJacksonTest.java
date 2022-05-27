package no.nav.foreldrepenger.common.domain;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.util.SerializationTestBase;

class OrgnummerRoundtripJacksonTest extends SerializationTestBase {

    @Test
    void orgnummerRoundtripTest () {
        test(new Orgnummer("999999999"), true);
    }
}

package no.nav.foreldrepenger.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class StringUtilTest {

    @Test
    void testPadFnr() {
        assertEquals("111111*****", StringUtil.partialMask("11111111111"));
    }

    @Test
    void testPadAllFnr() {
        assertEquals("***********", StringUtil.mask("11111111111"));
    }

    @Test
    void testPadListeMedFnr() {
        assertEquals(List.of("***********", "***********"), StringUtil.maskListe(List.of("11111111111", "22222222222")));
    }

    @Test
    void testPadAllNull() {
        assertEquals("<null>", StringUtil.mask(null));
    }

    @Test
    void testPadAllEmpty() {
        assertEquals("<null>", StringUtil.mask(""));
    }

}

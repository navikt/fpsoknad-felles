package no.nav.foreldrepenger.common.util;

import org.junit.jupiter.api.Test;

import static no.nav.foreldrepenger.common.util.LangUtil.toBoolean;
import static org.junit.jupiter.api.Assertions.*;

class LangUtilTest {


    @Test
    void mappingFraBooleanTilPrimitiveTest() {
        assertFalse(toBoolean(null));
        assertTrue(toBoolean(Boolean.TRUE));
        assertFalse(toBoolean(Boolean.FALSE));
    }

}

package no.nav.foreldrepenger.common.util;

import static no.nav.foreldrepenger.common.util.StringUtil.capitalizeFully;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class StringUtilTest {

    @Test
    void testPadFnr() {
        assertEquals("111111*****", StringUtil.partialMask("11111111111"));
        assertEquals("11*********", StringUtil.partialMask("11111111111", 2));
        assertEquals("111111***", StringUtil.partialMask("111111111"));
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

    @Test
    void testCapitalizeFully() {
        assertAll("Map fra uppercase til lowercase med stor forbokstav",
                () -> assertThat(capitalizeFully("FORNAVN MELLOMNAVN ETTERNAVN")).isEqualTo("Fornavn Mellomnavn Etternavn"),
                () -> assertThat(capitalizeFully("FORNAVN ETTERNAVN")).isEqualTo("Fornavn Etternavn"),
                () -> assertThat(capitalizeFully("PER PÅL ETTERNAVN")).isEqualTo("Per Pål Etternavn"),
                () -> assertThat(capitalizeFully("FORNAVN FØRSTEETTERNAVN ANDREETTERNAVN")).isEqualTo(
                        "Fornavn Førsteetternavn Andreetternavn"),
                () -> assertThat(capitalizeFully("FORNAVN1-FORNAVN2 ETTERNAVN")).isEqualTo("Fornavn1-Fornavn2 Etternavn"),
                () -> assertThat(capitalizeFully("TEST O'BRIAN")).isEqualTo("Test O'Brian"),
                () -> assertThat(capitalizeFully("EKSTRÂ FANCŸ ETTERNAVN")).isEqualTo("Ekstrâ Fancÿ Etternavn"),
                () -> assertThat(capitalizeFully("OLD MCDONALD")).isEqualTo("Old Mcdonald"));
    }


}

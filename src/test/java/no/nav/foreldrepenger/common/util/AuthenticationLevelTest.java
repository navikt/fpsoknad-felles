package no.nav.foreldrepenger.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationLevelTest {

    @Test
    void findNewAssuranceLevels() {
        assertThat(AuthenticationLevel.of("idporten-loa-substantial")).isEqualTo(AuthenticationLevel.IDPORTEN_LOA_SUBSTANTIAL);
        assertThat(AuthenticationLevel.of("idporten-loa-high")).isEqualTo(AuthenticationLevel.IDPORTEN_LOA_HIGH);
    }

    @Test
    void findOldAssuranceLevels() {
        assertThat(AuthenticationLevel.of("Level3")).isEqualTo(AuthenticationLevel.LEVEL3);
        assertThat(AuthenticationLevel.of("Level4")).isEqualTo(AuthenticationLevel.LEVEL4);
    }

    @Test
    void testFinnesIkke() {
        assertThat(AuthenticationLevel.of("Finnes_ikke_level")).isEqualTo(AuthenticationLevel.NONE);
    }
}

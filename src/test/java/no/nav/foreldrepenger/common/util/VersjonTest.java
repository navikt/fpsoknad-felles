package no.nav.foreldrepenger.common.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.innsending.SøknadType;

class VersjonTest {

    @Test
    void riktigVersjonForFagsystemType() {
        assertThat(Versjon.defaultVersjon(SøknadType.INITIELL_FORELDREPENGER)).isEqualTo(Versjon.V3);
        assertThat(Versjon.defaultVersjon(SøknadType.INITIELL_ENGANGSSTØNAD)).isEqualTo(Versjon.V3);
        assertThat(Versjon.defaultVersjon(SøknadType.INITIELL_SVANGERSKAPSPENGER)).isEqualTo(Versjon.V1);
        assertThat(Versjon.defaultVersjon(SøknadType.UKJENT)).isEqualTo(Versjon.V3);
    }
}

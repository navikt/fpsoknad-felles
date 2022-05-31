package no.nav.foreldrepenger.common.domain.foreldrepenger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.error.UnexpectedInputException;

class DekningsgradTest {

    @Test
    void dekningsgradOK() {
        assertThat(Dekningsgrad.fraKode(100)).isEqualTo(Dekningsgrad.HUNDRE);
        assertThat(Dekningsgrad.fraKode("100")).isEqualTo(Dekningsgrad.HUNDRE);
        assertThat(Dekningsgrad.fraKode(80)).isEqualTo(Dekningsgrad.ÅTTI);
        assertThat(Dekningsgrad.fraKode("80")).isEqualTo(Dekningsgrad.ÅTTI);
    }

    @Test
    void dekningsgradIkkeOK() {
        assertThatThrownBy(() -> Dekningsgrad.fraKode(99)).isInstanceOf(UnexpectedInputException.class);
        assertThatThrownBy(() -> Dekningsgrad.fraKode("99")).isInstanceOf(UnexpectedInputException.class);
        assertThatThrownBy(() -> Dekningsgrad.fraKode("ikke parsable")).isInstanceOf(NumberFormatException.class);
    }

}

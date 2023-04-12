package no.nav.foreldrepenger.common.domain.validation;

import static no.nav.foreldrepenger.common.domain.felles.TestUtils.engangssøknad;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.fødsel;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.nå;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.svp;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger;

class SøknadValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testSøknadMedFødselIDag() {
        assertThat(validator.validate(engangssøknad(fødsel(nå())))).isEmpty();
    }

    @Test
    void skalIkkeFøreTilValideringsfeilNårFødselsdatoErNull() {
        var svp = svp();
        assertThat(svp.getYtelse()).isInstanceOf(Svangerskapspenger.class);
        assertThat(((Svangerskapspenger) svp.getYtelse()).fødselsdato()).isNull();
        assertThat(validator.validate(svp())).isEmpty();
    }

}

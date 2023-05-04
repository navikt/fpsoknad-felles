package no.nav.foreldrepenger.common.domain.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;

public class RelasjonTilBarnValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void fødsel_kan_ha_avvik_mellom_antall_barn_og_oppgitte_fødselsdatoer() {
        var fødselsdatoer = List.of(
                LocalDate.now().minusMonths(4)
        );
        var fødsel = new Fødsel(3, fødselsdatoer, LocalDate.now().minusMonths(1), null);

        assertThat(validertOK(fødsel)).isTrue();
    }

    @Test
    void validering_ok_når_antall_fødsesldatoer_er_lik_antall_barn_oppgitt_ved_adopsjon() {
        var fødselsdatoer = List.of(
                LocalDate.now().minusMonths(4),
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(2)
        );
        var adopsjon = new Adopsjon(3, LocalDate.now(), false, false, null, LocalDate.now().plusMonths(2), fødselsdatoer);

        assertThat(validertOK(adopsjon)).isTrue();
    }

    @Test
    void validering_feiler_når_antall_fødsesldatoer_er_forskjellig_fra_antall_barn_oppgitt_ved_adopsjon() {
        var fødselsdatoer = List.of(
                LocalDate.now().minusMonths(4),
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4),
                LocalDate.now().minusYears(4)
        );
        var adopsjon = new Adopsjon(3, LocalDate.now(), false, false, null, LocalDate.now().plusMonths(2), fødselsdatoer);

        assertThat(validertOK(adopsjon)).isFalse();
    }

    private boolean validertOK(RelasjonTilBarn relasjonTilBarn) {
        var validate = validator.validate(relasjonTilBarn);
        return validate.isEmpty();
    }
}

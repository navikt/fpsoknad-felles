package no.nav.foreldrepenger.common.domain.felles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class NavnOrgTest extends SerializationTestBase {

    @Test
    void navnEquals() {
        var n = new Navn("Ole", "Mellomnavn", "Skrivepult");
        var n1 = new Navn("Ole", "Mellomnavn", "Skrivepult");
        assertEquals(n, n1);
    }

    @Test
    void navneFormattering() {
        var enkeltNavn = new Navn("FORNAVN", "MELLOMNAVN", "ETTERNAVN");
        var ikkeMellomnavn = new Navn("FORNAVN", null, "IKKE-MELLOMNAVN");
        var dobbeltFornavn = new Navn("PER PÅL", null, "ETTERNAVN");
        var dobbeltEtternavn = new Navn("FORNAVN", null, "FØRSTEETTERNAVN ANDREETTERNAVN");
        var fancyNavn = new Navn("EKSTRÂ FANCŸ", null, "ETTERNAVN");
        var ekstraWhitespace = new Navn("EKSTRA       WHITESPACE", null, "ETTERNAVN");
        var oldMcDonald = new Navn("OLD", null, "MCDONALD");

        assertAll("Navn skal mappes fra uppercase til lowercase med stor forbokstav",
                () -> assertThat(enkeltNavn.navn()).isEqualTo("Fornavn Mellomnavn Etternavn"),
                () -> assertThat(ikkeMellomnavn.navn()).isEqualTo("Fornavn Ikke-mellomnavn"),
                () -> assertThat(dobbeltFornavn.navn()).isEqualTo("Per Pål Etternavn"),
                () -> assertThat(dobbeltEtternavn.navn()).isEqualTo("Fornavn Førsteetternavn Andreetternavn"),
                () -> assertThat(fancyNavn.navn()).isEqualTo("Ekstrâ Fancÿ Etternavn"),
                () -> assertThat(ekstraWhitespace.navn()).isEqualTo("Ekstra Whitespace Etternavn"),
                () -> assertThat(oldMcDonald.navn()).isEqualTo("Old Mcdonald"));
    }
}

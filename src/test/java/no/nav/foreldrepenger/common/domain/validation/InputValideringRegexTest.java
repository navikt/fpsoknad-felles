package no.nav.foreldrepenger.common.domain.validation;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.BARE_BOKSTAVER;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.BARE_TALL;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.BOKSTAVER_MED_MELLOMROM;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.NORSK_FØDSELSNUMMER;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.ORGNUMMER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class InputValideringRegexTest {

    @Test
    void fritektsSkalVæreGyldigOgIkkeStoppesAvRegexen() {
        var tekst = """
                en helt vanlig tekts med litt linje skift \n
                litt tab \t og \n andre spesialtegn som ,.-_@41235()?...
                2+2=4 mens 3-1=2. WOW SO CAPS! öäåØÆÅÉÜéü?«»�""'§´´`
                """;
        assertThat(verifiser(FRITEKST, tekst)).isTrue();
    }

    @Test
    void verifsierGyldigFritekst() {
        var tekst = "godtar litt forskjellige ´'`´''§€§§";
        assertThat(verifiser(FRITEKST, tekst)).isTrue();
    }

    @Test
    void orgnummerRegexTillaterBareTallOgSkalHaLengde9() {
        assertThat(verifiser(ORGNUMMER, "123456789")).isTrue();
        assertThat(verifiser(ORGNUMMER, "123456789123")).isFalse();
        assertThat(verifiser(ORGNUMMER, "123456789 123456789")).isFalse();
        assertThat(verifiser(ORGNUMMER, "1234")).isFalse();
        assertThat(verifiser(ORGNUMMER, "1234abcde")).isFalse();
        assertThat(verifiser(ORGNUMMER, "")).isFalse();
        assertThat(verifiser(ORGNUMMER, "1.2345678")).isFalse();
    }

    @Test
    void fødselsnummerRegexTillaterBareTallOgSkalHaLengde11() {
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "12345678910")).isTrue();
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "12345678912323231232323")).isFalse();
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "12345678910 12345678910")).isFalse();
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "12343")).isFalse();
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "123456abcde")).isFalse();
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "")).isFalse();
        assertThat(verifiser(NORSK_FØDSELSNUMMER, "1.234567891")).isFalse();
    }

    @Test
    void bokstaverMedMellomromRegexTillaterAlleBokstaverOgMellmrom() {
        assertThat(verifiser(BOKSTAVER_MED_MELLOMROM, "Hei på dei")).isTrue();
        assertThat(verifiser(BOKSTAVER_MED_MELLOMROM, "IngenMellomromHeräöéüU")).isTrue();
        assertThat(verifiser(BOKSTAVER_MED_MELLOMROM, "Ikke tillatt!")).isFalse();
        assertThat(verifiser(BOKSTAVER_MED_MELLOMROM, "<Ikke tillatt>")).isFalse();
        assertThat(verifiser(BOKSTAVER_MED_MELLOMROM, "Per 25 Pål")).isFalse();
    }

    @Test
    void bareBokstaverTillaterIkkeMellomromOgAndreTingSomIkkeErBokstaver() {
        assertThat(verifiser(BARE_BOKSTAVER, "IngenMellomromHeräöéüU")).isTrue();
        assertThat(verifiser(BARE_BOKSTAVER, "Hei på dei")).isFalse();
        assertThat(verifiser(BARE_BOKSTAVER, "Ikke tillatt!")).isFalse();
        assertThat(verifiser(BARE_BOKSTAVER, "<Ikke tillatt>")).isFalse();
        assertThat(verifiser(BARE_BOKSTAVER, "Per 25 Pål")).isFalse();
    }

    @Test
    void bareTallTillaterIkkeMellomromOgAndreTingSomIkkeErTall() {
        assertThat(verifiser(BARE_TALL, "123454372384181")).isTrue();
        assertThat(verifiser(BARE_TALL, "1")).isTrue();
        assertThat(verifiser(BARE_TALL, "")).isTrue();
        assertThat(verifiser(BARE_TALL, "12312312  21312312312")).isFalse();
        assertThat(verifiser(BARE_TALL, "Hei på dei")).isFalse();
        assertThat(verifiser(BARE_TALL, "Ikke tillatt!")).isFalse();
        assertThat(verifiser(BARE_TALL, "<Ikke tillatt>")).isFalse();
        assertThat(verifiser(BARE_TALL, "Per 25 Pål")).isFalse();
    }

    private boolean verifiser(String regex, String verdi) {
        var pattern = Pattern.compile(regex);
        return pattern.matcher(verdi).matches();
    }

}

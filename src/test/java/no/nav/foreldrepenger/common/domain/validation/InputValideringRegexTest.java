package no.nav.foreldrepenger.common.domain.validation;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST_I_JSON_STRING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class InputValideringRegexTest {

    @Test
    void fritektsSkalVæreGyldigOgIkkeStoppesAasdasdvRegexen() {
        var tekst = """
                En helt vanlig streng ØÆÅ
                """;
        assertThat(verifiser(FRITEKST, tekst)).isTrue();
    }

    @Test
    void fritektsSkalVæreGyldigOgIkkeStoppesAvRegexen() {
        var tekst = """
                en helt vanlig tekts med litt linje skift \n
                litt tab \t og \n andre spesialtegn som ,.-_@41235()?...
                2+2=4 mens 3-1=2. WOW SO CAPS! öäåØÆÅÉÜéü?
                """;
        assertThat(verifiser(FRITEKST, tekst)).isTrue();
    }

    @Test
    void tekstSomInneholderUlovligTegnBlirAvvistAvRegex() {
        var tekst = """
                En helt vanlig tekts med litt linje skift,
                men har følgende ulovlig tegn <>
                """;
        assertThat(verifiser(FRITEKST, tekst)).isFalse();
    }

    @Test
    void tekstSomInneholderUlovligTegnBlirAvvistAvRegex2() {
        var tekst = """
                En helt vanlig tekts med litt linje skift,
                men har følgende ulovlig tegn #D32
                """;
        assertThat(verifiser(FRITEKST, tekst)).isFalse();
    }

    @Test
    void tekstSomInneholderUlovligTegnBlirAvvistAvRegex3() {
        var tekst = """
                En helt vanlig tekts med litt linje skift \n
                litt tab \t og andre spesialtegn som ,.-_@41235()?...
                MEN... Ulovlig tegn: {}
                """;
        assertThat(verifiser(FRITEKST, tekst)).isFalse();
    }

    @Test
    void tekstSomInneholderUlovligTegnBlirAvvistAvRegex4() {
        var tekst = """
                En helt vanlig tekts med litt linje skift \n
                litt tab \t og andre spesialtegn som ,.-_@41235()?...
                MEN... Ulovlig tegn: \\
                """;
        assertThat(verifiser(FRITEKST, tekst)).isFalse();
    }


    @Test
    void jsonObjektSomSendesSomEnStrengSkalIkkeBlokkeresVedValidering() {
        var tekst = """
                {"søknad":{"tilleggsopplysninger":{"begrunnelseForSenEndring":{"tekst":"idas a\n\ndansa \nand
                sdal ads\n\nsadlc\nl\nllcear   æø","ekstraInformasjon":"ARBEID"}}}}
                "currentRoute": "/soknad/uttaksplan-info"  {} ,.,++ë@hotmail.no--Sopra/
                {"søknad":{"type":"foreldrepenger","harGodkjentVilkår":true,"søkersituasjon":{"situasjon":"adopsjon",
                {"tekst":"idas a\n\ndansa \nand sdal \sads\n\nsadlc\nl\nllcear   æø","ekstraInformasjon":"ARBEID"}}}
                ,"version":3,"currentRoute":"/soknad/utenlandsopphold","uttaksplanInfo":{"harAnnenForelderSøktFP":true,
                """;
        assertThat(verifiser(FRITEKST_I_JSON_STRING, tekst)).isTrue();
    }

    @Test
    void jsonObjektSomSendesSomEnStrengSkalBlokkeresVedUlovligTegn() {
        var tekst = """
                {
                    "body": "litt Lovlig Tekst\n Hva med noe",
                    "bodyUlovlig": "superDuper\\^"
                    }
                """;
        assertThat(verifiser(FRITEKST_I_JSON_STRING, tekst)).isFalse();
    }



    private boolean verifiser(String regex, String verdi) {
        var pattern = Pattern.compile(regex);
        return pattern.matcher(verdi).matches();
    }

}

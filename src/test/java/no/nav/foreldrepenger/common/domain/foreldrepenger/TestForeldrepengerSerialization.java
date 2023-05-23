package no.nav.foreldrepenger.common.domain.foreldrepenger;

import static java.util.Collections.singletonList;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.adopsjon;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.person;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.annenOpptjening;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.endringssøknad;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.ettersending;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.fordeling;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.foreldrepenger;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.gradertPeriode;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.norskEgenNæring;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.norskForelder;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.omsorgsovertakelse;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.oppholdsPeriode;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.opptjening;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.overføringsPeriode;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.rettigheter;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.termin;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.utenlandskArbeidsforhold;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.utenlandskEgenNæring;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.utenlandskForelder;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.utsettelsesPeriode;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.uttaksPeriode;
import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.åpenPeriode;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.bytesFra;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.Saksnummer;
import no.nav.foreldrepenger.common.domain.felles.DokumentType;
import no.nav.foreldrepenger.common.domain.felles.InnsendingsType;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggMetaData;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UkjentForelder;
import no.nav.foreldrepenger.common.domain.felles.opptjening.EgenNæring;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.FPSakFordeltKvittering;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.GosysKvittering;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.PendingKvittering;
import no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils;
import no.nav.foreldrepenger.common.util.SerializationTestBase;


class TestForeldrepengerSerialization extends SerializationTestBase {

    @Test
    void testGosysKvittering() {
        test(new GosysKvittering("42"), false);
    }

    @Test
    void testProsentAndel() {
        ProsentAndel orig = new ProsentAndel(40.0);
        ProsentAndel orig1 = new ProsentAndel(40d);
        test(orig, true);
        test(orig1, false);
    }

    private record ProsentKlasse(ProsentAndel prosent) {
    }

    @Test
    void testProsentAndelIKlasseRoundtripTest() throws Exception {
        var orig = new ProsentKlasse(new ProsentAndel(40d));
//        assertEquals(orig, mapper.readValue("{ \"prosent\" : 40 }", ProsentKlasse.class));
        assertEquals(orig, mapper.readValue("{ \"prosent\" : 40.0 }", ProsentKlasse.class));
//        assertEquals(orig, mapper.readValue("{ \"prosent\" : \"40\" }", ProsentKlasse.class));
//        assertEquals(orig, mapper.readValue("{ \"prosent\" : \"40.0\" }", ProsentKlasse.class));
    }

    @Test
    void testPollKvittering() {
        test(new PendingKvittering(Duration.ofSeconds(6)), false);
    }

    @Test
    void testFordeltKvittering() {
        test(new FPSakFordeltKvittering("123", new Saksnummer("456")), false);
    }

    @Test
    void testDekningsgrad() {
        test(Dekningsgrad.HUNDRE, true);
    }

    @Test
    void testPerson() {
        test(person());
    }

    @Test
    void testEttersending() {
        test(ettersending(), false);
    }

    @Test
    void testEndringssøknad() {
        test(endringssøknad(), true);
    }

    @Test
    void testForeldrepenger() {
        test(foreldrepenger(false), true);
    }

    @Test
    void testSøknad() {
        test(ForeldrepengerTestUtils.foreldrepengesøknadMedEttIkkeOpplastedVedlegg(false), true);
    }

    @Test
    void testOpptjening() {
        test(opptjening());
    }

    @Test
    void testRettigheter() {
        test(rettigheter());
    }

    @Test
    void testUkjentForelder() {
        test(new UkjentForelder());
    }

    @Test
    void testStønadskontoType() {
        test(StønadskontoType.IKKE_SATT, false);
    }

    @Test
    void testVedleggMetadata() {
        test(new VedleggMetaData(new VedleggReferanse("42"), InnsendingsType.LASTET_OPP, DokumentType.I000002));
    }

    @Test
    void testUtenlandskForelder() {
        test(utenlandskForelder());
    }

    @Test
    void testNorskForelder() {
        test(norskForelder());
    }

    @Test
    void testFordeling() {
        test(fordeling(), false);
    }

    @Test
    void testUttaksPeride() {
        test(uttaksPeriode(), false);
    }

    @Test
    void testGradertPeriode() {
        test(gradertPeriode(), true);
    }

    @Test
    void testOverføringsperiode() {
        test(overføringsPeriode(), false);
    }

    @Test
    void testOppholdsPeriode() {
        test(oppholdsPeriode());
    }

    @Test
    void testUtsettelsesPeriode() {
        test(utsettelsesPeriode());
    }

    @Test
    void testÅpenPeriode() {
        test(åpenPeriode());
    }

    @Test
    void testAdopsjon() {
        test(adopsjon());
    }

    @Test
    void testOmsorgsovertagelsse() {
        test(omsorgsovertakelse());
    }

    @Test
    void testTermin() {
        test(termin());
    }

    @Test
    void testAnnenOpptjening() {
        test(annenOpptjening());
    }

    @Test
    void testUtenlandskArbeidsforhold() {
        test(utenlandskArbeidsforhold(), false);
    }

    @Test
    void relasjonTilBarn() {
        RelasjonTilBarn f = new Fødsel(
                1,
                singletonList(LocalDate.now()),
                null,
                null
        );

        test(f, true);
        f = new FremtidigFødsel(LocalDate.now(), LocalDate.now());
        test(f, true);
        f = new Adopsjon(1, LocalDate.now(), true, false, null, null, null);
        test(f, true);
    }

    @Test
    void testEgenNæringUtenlandskOrganisasjon() throws Exception {
        assertEquals(CountryCode.UG, mapper.readValue(bytesFra("json/utenlandskOrg.json"), EgenNæring.class).registrertILand());

        test(utenlandskEgenNæring(), true);
    }

    @Test
    void testEgenNæringNorskorganisasjon() {
        test(norskEgenNæring(), true);
    }


}

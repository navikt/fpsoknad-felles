package no.nav.foreldrepenger.common.domain.domain.foreldrepenger;

import static no.nav.foreldrepenger.common.domain.felles.TestUtils.adopsjon;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.person;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.annenOpptjening;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.endringssøknad;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.ettersending;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.fordeling;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.foreldrepenger;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.gradertPeriode;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.norskEgenNæring;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.norskForelder;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.omsorgsovertakelse;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.oppholdsPeriode;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.opptjening;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.overføringsPeriode;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.rettigheter;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.termin;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.utenlandskArbeidsforhold;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.utenlandskEgenNæring;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.utenlandskForelder;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.utsettelsesPeriode;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.uttaksPeriode;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils.åpenPeriode;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.bytesFra;
import static no.nav.foreldrepenger.common.util.Versjon.DEFAULT_VERSJON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.felles.DokumentType;
import no.nav.foreldrepenger.common.domain.felles.InnsendingsType;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggMetaData;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UkjentForelder;
import no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskOrganisasjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Dekningsgrad;
import no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType;
import no.nav.foreldrepenger.common.domain.util.SerializationTestBase;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.FPSakFordeltKvittering;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.GosysKvittering;
import no.nav.foreldrepenger.common.innsending.foreldrepenger.PendingKvittering;


class TestForeldrepengerSerialization extends SerializationTestBase {

    @Test
    void testGosysKvittering() {
        test(new GosysKvittering("42"), false);
    }

    @Test
    void testProsentAndel() throws Exception {
        ProsentAndel orig = new ProsentAndel(40.0);
        ProsentAndel orig1 = new ProsentAndel(40);
        test(orig, false);
        test(orig1, false);
        assertEquals(orig, mapper.readValue("{ \"p1\" : 40}", ProsentAndel.class));
        assertEquals(orig, mapper.readValue("{ \"p2\" : 40.0}", ProsentAndel.class));
        orig = new ProsentAndel(40);
        test(orig, false);
        assertEquals(orig, mapper.readValue("{ \"p1\" : 40}", ProsentAndel.class));
        assertEquals(orig, mapper.readValue("{ \"p2\" : 40.0}", ProsentAndel.class));

    }

    @Test
    void testPollKvittering() {
        test(new PendingKvittering(Duration.ofSeconds(6)), false);
    }

    @Test
    void testFordeltKvittering() {
        test(new FPSakFordeltKvittering("123", "456"), false);
    }

    @Test
    void testDekningsgrad() {
        test(Dekningsgrad.GRAD100, false);
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
        test(endringssøknad(DEFAULT_VERSJON), false);
    }

    @Test
    void testForeldrepenger() {
        test(foreldrepenger(DEFAULT_VERSJON, false), true);
    }

    @Test
    void testSøknad() {
        test(ForeldrepengerTestUtils.søknadMedEttIkkeOpplastedVedlegg(DEFAULT_VERSJON, false), true);
    }

    @Test
    void testOpptjening() {
        test(opptjening(DEFAULT_VERSJON));
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

    // TODO: Fjerne dette?
//    @Test
//    void testSøknadMetadata() {
//        test(new SøknadMetadata(new SøknadEgenskap(Versjon.V1, SøknadType.INITIELL_FORELDREPENGER), "42"), true);
//    }
//
//    @Test
//    void testSøknadInspeksjon() throws JsonProcessingException {
//        test(new SøknadEgenskap(Versjon.V1, SøknadType.INITIELL_FORELDREPENGER), false);
//    }

    @Test
    void testVedleggMetadata() {
        test(new VedleggMetaData("42", InnsendingsType.LASTET_OPP, DokumentType.I000002));
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
        test(fordeling(DEFAULT_VERSJON), false);

    }

    @Test
    void testUttaksPeride() {
        test(uttaksPeriode(), false);
    }

    @Test
    void testGradertPeriode() {
        test(gradertPeriode(DEFAULT_VERSJON), false);
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
        test(annenOpptjening(DEFAULT_VERSJON));
    }

    @Test
    void testUtenlandskArbeidsforhold() {
        test(utenlandskArbeidsforhold(), false);
    }

    @Test
    void relasjonTilBarn() {
        RelasjonTilBarn f = new Fødsel(LocalDate.now());
        test(f, true);
        f = new FremtidigFødsel(LocalDate.now(), LocalDate.now());
        test(f, true);
        f = new Adopsjon(1, LocalDate.now(), true, false, null, null, null);
        test(f, true);
    }

    @Test
    void testEgenNæringUtenlandskOrganisasjon() throws Exception {
        assertEquals(CountryCode.UG, mapper.readValue(bytesFra("json/utenlandskOrg.json"), UtenlandskOrganisasjon.class).getRegistrertILand());

        test(utenlandskEgenNæring(), true);
    }

    @Test
    void testEgenNæringNorskorganisasjon() {
        test(norskEgenNæring());
    }


}

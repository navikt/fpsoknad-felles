package no.nav.foreldrepenger.common.domain.domain.engangsstønad;

import static no.nav.foreldrepenger.common.domain.felles.TestUtils.engangssøknad;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.engangstønad;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.fødsel;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.norskForelder;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.påkrevdVedlegg;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.termin;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.ukjentForelder;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.utenlandskForelder;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Kvittering;
import no.nav.foreldrepenger.common.domain.LeveranseStatus;
import no.nav.foreldrepenger.common.domain.domain.SerializationTestBase;
import no.nav.foreldrepenger.common.domain.felles.TestUtils;
import no.nav.foreldrepenger.common.domain.foreldrepenger.ForeldrepengerTestUtils;

class TestEngangsstønadSerialization extends SerializationTestBase {

    @Test
    void testKvittering() {
        var kvittering = new Kvittering(LeveranseStatus.SENDT_OG_FORSØKT_BEHANDLET_FPSAK);
        kvittering.setJournalId("555");
        kvittering.setSaksNr("666");
        test(kvittering, false);
    }

    @Test
    void testVedlegg() {
        test(påkrevdVedlegg("pdf/terminbekreftelse.pdf"), false);
    }

    @Test
    void testSøknadNorge() {
        var engangssøknad = engangssøknad(false, fødsel(), norskForelder(),
                påkrevdVedlegg(ForeldrepengerTestUtils.ID142));
        test(engangssøknad, false);
    }

    @Test
    void testEngangsstønadNorge() {
        var engangstønad = engangstønad(false, termin(), norskForelder());
        test(engangstønad, false);
    }

    @Test
    void testEngangsstønadUtland() {
        test(TestUtils.engangstønad(true, termin(), utenlandskForelder()), false);
    }

    @Test
    void testEngangsstønadUkjentFar() {
        test(engangstønad(true, termin(), ukjentForelder()), false);
    }

    @Test
    void testNorskAnnenForelder() {
        test(norskForelder(), false);
    }

    @Test
    void testUtenlandskAnnenForelder() {
        test(utenlandskForelder(), false);
    }

    @Test
    void testUkjentForelder() {
        test(ukjentForelder(), false);
    }

    @Test
    void testMedlemsskap() {
        test(TestUtils.medlemsskap(false));
    }

    @Test
    void testMedlemsskapUtland() {
        test(TestUtils.medlemsskap(false));
    }

    @Test
    void testFnr() {
        test(new Fødselsnummer("22222233333"), true);
    }

    @Test
    void testAktør() {
        test(new AktørId("111111111"), true);
    }

    @Test
    void testAdopsjon() {
        test(TestUtils.adopsjon());
    }

    @Test
    void testFødsel() {
        test(fødsel(), false);
    }

    @Test
    void testFremtidigOppholdNorge() {
        test(TestUtils.framtidigOppholdINorge(), false);
    }

    @Test
    void testFremtidigOppholdUtland() {
        test(TestUtils.framtidigOppHoldIUtlandet(), false);
    }

    @Test
    void testOmsorgsovertagkelse() {
        test(TestUtils.omsorgsovertakelse());
    }

    @Test
    void testSøker() {
        test(TestUtils.søker());
    }

    @Test
    void testAktorId() {
        test(TestUtils.aktoer());
    }

    @Test
    void testTermin() {
        test(termin());
    }

    @Test
    void testUtenlandsopphold() {
        test(TestUtils.utenlandsopphold(), true);
    }

    @Test
    void testVarighet() {
        test(TestUtils.varighet());
    }
}

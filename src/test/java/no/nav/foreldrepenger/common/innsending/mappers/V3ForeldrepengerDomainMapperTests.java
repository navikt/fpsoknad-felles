package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.NORSK_FORELDER_FNR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.felles.ValgfrittVedlegg;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.oppslag.Oppslag;
import no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.AnnenForelderMedNorskIdent;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Termin;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Gradering;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Oppholdsperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Overfoeringsperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Utsettelsesperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Uttaksperiode;

class V3ForeldrepengerDomainMapperTests {


    private Oppslag oppslag;

    @BeforeEach
    void setUp() {
        oppslag = mock(Oppslag.class);
    }

    @Test
    void sjekkerKorrektMappingFraJsonTilXmlObjekt() {
        var aktørIdSøker = new AktørId("123456789");
        var aktørIdAnnenpart = new AktørId("123456789");
        when(oppslag.aktørId(NORSK_FORELDER_FNR)).thenReturn(aktørIdAnnenpart);
        var mapper = new V3ForeldrepengerDomainMapper(oppslag);

        var søknad = ForeldrepengerTestUtils.foreldrepengeSøknad();
        var søknadXML = mapper.tilModell(søknad, aktørIdSøker);
        assertThat(søknadXML.getSoeker().getAktoerId()).isEqualTo(aktørIdSøker.value());
        assertThat(søknadXML.getSoeker().getSoeknadsrolle().getKode()).isEqualTo(søknad.getSøker().getSøknadsRolle().name());

        assertThat(søknadXML.getMottattDato()).isEqualTo(søknad.getMottattdato());
        assertThat(søknad.getVedlegg())
                .hasExactlyElementsOfTypes(ValgfrittVedlegg.class)
                .hasSameSizeAs(søknadXML.getAndreVedlegg())
                .hasSize(1);
        assertThat(søknadXML.getPaakrevdeVedlegg()).isEmpty();

        // Foreldrepenger ytelse
        var foreldrepenger = (no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger) søknad.getYtelse();
        var foreldrepengerXMLO = mapper.tilForeldrepenger(foreldrepenger);

        var rettigheterJSON = foreldrepenger.getRettigheter();
        var rettigheterXML = foreldrepengerXMLO.getRettigheter();
        assertThat(rettigheterJSON.harAnnenForelderRett()).isEqualTo(rettigheterXML.isHarAnnenForelderRett());
        assertThat(rettigheterJSON.harAleneOmsorgForBarnet()).isEqualTo(rettigheterXML.isHarAleneomsorgForBarnet());
        assertThat(rettigheterJSON.harMorUføretrygd()).isEqualTo(rettigheterXML.isHarMorUforetrygd());
        assertThat(rettigheterJSON.harMorForeldrepengerEØS()).isEqualTo(rettigheterXML.isHarMorForeldrepengerEOS());

        // AnnenForelder
        assertThat(foreldrepenger.getAnnenForelder()).isInstanceOf(NorskForelder.class);
        assertThat(foreldrepengerXMLO.getAnnenForelder()).isInstanceOf(AnnenForelderMedNorskIdent.class);
        assertThat(((NorskForelder) foreldrepenger.getAnnenForelder()).getFnr()).isEqualTo(NORSK_FORELDER_FNR);
        assertThat(((AnnenForelderMedNorskIdent) foreldrepengerXMLO.getAnnenForelder()).getAktoerId()).isEqualTo(aktørIdAnnenpart.value());

        // RelasjonTilBarn
        var fremtidigFødselJSON = (FremtidigFødsel) foreldrepenger.getRelasjonTilBarn();
        var terminXML = (Termin) foreldrepengerXMLO.getRelasjonTilBarnet();
        assertThat((fremtidigFødselJSON.getAntallBarn())).isEqualTo(terminXML.getAntallBarn());
        assertThat((fremtidigFødselJSON.getTerminDato())).isEqualTo(terminXML.getTermindato());
        assertThat((fremtidigFødselJSON.getUtstedtDato())).isEqualTo(terminXML.getUtstedtdato());

        // Dekningsgrad
        assertThat(String.valueOf(foreldrepenger.getDekningsgrad().kode())).isEqualTo(foreldrepengerXMLO.getDekningsgrad().getDekningsgrad().getKode());

        // Opptjening
        assertThat(foreldrepengerXMLO.getOpptjening().getUtenlandskArbeidsforhold())
                .hasSameSizeAs(foreldrepenger.getOpptjening().getUtenlandskArbeidsforhold())
                .hasSize(1);
        assertThat(foreldrepengerXMLO.getOpptjening().getEgenNaering())
                .hasSameSizeAs(foreldrepenger.getOpptjening().getEgenNæring())
                .hasSize(2);
        assertThat(foreldrepengerXMLO.getOpptjening().getAnnenOpptjening())
                .hasSameSizeAs(foreldrepenger.getOpptjening().getAnnenOpptjening())
                .hasSize(1);
        assertThat(foreldrepengerXMLO.getOpptjening().getFrilans().getFrilansoppdrag())
                .hasSameSizeAs(foreldrepenger.getOpptjening().getFrilans().frilansOppdrag())
                .hasSize(3);

        // Fordeling
        assertThat(foreldrepenger.getFordeling().getPerioder())
                .hasSameSizeAs(foreldrepengerXMLO.getFordeling().getPerioder())
                .hasSize(5);
        assertThat(foreldrepengerXMLO.getFordeling().getPerioder())
                .hasExactlyElementsOfTypes(
                        Oppholdsperiode.class,
                        Overfoeringsperiode.class,
                        Utsettelsesperiode.class,
                        Uttaksperiode.class,
                        Gradering.class
                );

        // Medlemsskap
        assertThat(foreldrepengerXMLO.getMedlemskap().getOppholdUtlandet())
                .hasSize(
                        foreldrepenger.getMedlemsskap().getTidligereOppholdsInfo().getUtenlandsOpphold().size() +
                        foreldrepenger.getMedlemsskap().getFramtidigOppholdsInfo().getUtenlandsOpphold().size()
                );
        assertThat(foreldrepengerXMLO.getMedlemskap().isINorgeVedFoedselstidspunkt()).isTrue();
        assertThat(foreldrepengerXMLO.getMedlemskap().isBoddINorgeSiste12Mnd()).isTrue();
        assertThat(foreldrepengerXMLO.getMedlemskap().isBorINorgeNeste12Mnd()).isTrue();

    }
}

package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils.NORSK_FORELDER_FNR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.felles.ValgfrittVedlegg;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger;
import no.nav.foreldrepenger.common.util.ForeldrepengerTestUtils;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.AnnenForelderMedNorskIdent;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Termin;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Gradering;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Oppholdsperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Overfoeringsperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Utsettelsesperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Uttaksperiode;

class V3ForeldrepengerDomainMapperTests {

    @Test
    void sjekkerKorrektMappingFraJsonTilXmlObjekt() {
        var aktørIdSøker = new AktørId("123456789");
        var aktørIdAnnenpart = new AktørId("123456789");
        var mapper = new V3ForeldrepengerDomainMapper(fnr -> aktørIdAnnenpart);

        var søknad = ForeldrepengerTestUtils.foreldrepengesøknad();
        var søknadXML = mapper.tilModell(søknad, aktørIdSøker);
        assertThat(søknadXML.getSoeker().getAktoerId()).isEqualTo(aktørIdSøker.value());
        assertThat(søknadXML.getSoeker().getSoeknadsrolle().getKode()).isEqualTo(søknad.getSøker().søknadsRolle().name());

        assertThat(søknadXML.getMottattDato()).isEqualTo(søknad.getMottattdato());
        assertThat(søknad.getVedlegg())
                .hasExactlyElementsOfTypes(ValgfrittVedlegg.class)
                .hasSameSizeAs(søknadXML.getAndreVedlegg())
                .hasSize(1);
        assertThat(søknadXML.getPaakrevdeVedlegg()).isEmpty();

        // Foreldrepenger ytelse
        var foreldrepenger = (Foreldrepenger) søknad.getYtelse();
        var foreldrepengerXMLO = mapper.tilForeldrepenger(foreldrepenger);

        var rettigheterJSON = foreldrepenger.rettigheter();
        var rettigheterXML = foreldrepengerXMLO.getRettigheter();
        assertThat(rettigheterJSON.harAnnenForelderRett()).isEqualTo(rettigheterXML.isHarAnnenForelderRett());
        assertThat(rettigheterJSON.harAleneOmsorgForBarnet()).isEqualTo(rettigheterXML.isHarAleneomsorgForBarnet());
        assertThat(rettigheterJSON.harMorUføretrygd()).isEqualTo(rettigheterXML.isHarMorUforetrygd());
        assertThat(rettigheterJSON.harAnnenForelderTilsvarendeRettEØS()).isEqualTo(rettigheterXML.isHarAnnenForelderTilsvarendeRettEOS());
        assertThat(rettigheterJSON.harAnnenForelderOppholdtSegIEØS()).isEqualTo(rettigheterXML.isHarAnnenForelderOppholdtSegIEOS());

        // AnnenForelder
        assertThat(foreldrepenger.annenForelder()).isInstanceOf(NorskForelder.class);
        assertThat(foreldrepengerXMLO.getAnnenForelder()).isInstanceOf(AnnenForelderMedNorskIdent.class);
        assertThat(((NorskForelder) foreldrepenger.annenForelder()).fnr()).isEqualTo(NORSK_FORELDER_FNR);
        assertThat(((AnnenForelderMedNorskIdent) foreldrepengerXMLO.getAnnenForelder()).getAktoerId()).isEqualTo(aktørIdAnnenpart.value());

        // RelasjonTilBarn
        var fremtidigFødselJSON = (FremtidigFødsel) foreldrepenger.relasjonTilBarn();
        var terminXML = (Termin) foreldrepengerXMLO.getRelasjonTilBarnet();
        assertThat((fremtidigFødselJSON.getAntallBarn())).isEqualTo(terminXML.getAntallBarn());
        assertThat((fremtidigFødselJSON.getTerminDato())).isEqualTo(terminXML.getTermindato());
        assertThat((fremtidigFødselJSON.getUtstedtDato())).isEqualTo(terminXML.getUtstedtdato());

        // Dekningsgrad
        assertThat(String.valueOf(foreldrepenger.dekningsgrad().kode())).isEqualTo(foreldrepengerXMLO.getDekningsgrad().getDekningsgrad().getKode());

        // Opptjening
        assertThat(foreldrepengerXMLO.getOpptjening().getUtenlandskArbeidsforhold())
                .hasSameSizeAs(foreldrepenger.opptjening().utenlandskArbeidsforhold())
                .hasSize(1);
        assertThat(foreldrepengerXMLO.getOpptjening().getEgenNaering())
                .hasSameSizeAs(foreldrepenger.opptjening().egenNæring())
                .hasSize(2);
        assertThat(foreldrepengerXMLO.getOpptjening().getAnnenOpptjening())
                .hasSameSizeAs(foreldrepenger.opptjening().annenOpptjening())
                .hasSize(1);
        assertThat(foreldrepengerXMLO.getOpptjening().getFrilans().getFrilansoppdrag())
                .isEmpty();

        // Fordeling
        var fordeling = foreldrepenger.fordeling();
        assertThat(fordeling.perioder())
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
        assertThat(fordeling.ønskerJustertUttakVedFødsel()).isTrue();

        // Medlemsskap
        assertThat(foreldrepengerXMLO.getMedlemskap().getOppholdUtlandet()).hasSize(foreldrepenger.utenlandsopphold().opphold().size());
        assertThat(foreldrepengerXMLO.getMedlemskap().isINorgeVedFoedselstidspunkt()).isTrue();
    }

    @Test
    void svpMappingGammelTilrettelegging() {
        var svp = ((Svangerskapspenger) ForeldrepengerTestUtils.svp().getYtelse());
        var svpXml = V1SvangerskapspengerDomainMapper.tilSvangerskapspenger(svp);
        assertThat(svpXml.getTilretteleggingListe().getTilrettelegging()).hasSize(3);
    }


}

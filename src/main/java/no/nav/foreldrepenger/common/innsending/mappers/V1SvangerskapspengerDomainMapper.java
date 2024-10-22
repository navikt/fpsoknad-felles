package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.innsending.mappers.MapperEgenskaper.SVANGERSKAPSPENGER;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.medlemsskapFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.målformFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.opptjeningFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.søkerFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.tilVedlegg;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.vedleggFra;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.xml.bind.JAXBElement;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Endringssøknad;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.Tilretteleggingbehov;
import no.nav.foreldrepenger.common.error.UnexpectedInputException;
import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import no.nav.foreldrepenger.common.innsending.mappers.jaxb.SVPV1JAXBUtil;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Arbeidsforhold;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Arbeidsgiver;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.AvtaltFerie;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.AvtaltFerieListe;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.DelvisTilrettelegging;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Frilanser;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.HelTilrettelegging;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.IngenTilrettelegging;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.PrivatArbeidsgiver;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.SelvstendigNæringsdrivende;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Svangerskapspenger;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Tilrettelegging;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.TilretteleggingListe;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Virksomhet;
import no.nav.vedtak.felles.xml.soeknad.v3.OmYtelse;
import no.nav.vedtak.felles.xml.soeknad.v3.Soeknad;

public class V1SvangerskapspengerDomainMapper implements DomainMapper {
    private static final SVPV1JAXBUtil jaxb = new SVPV1JAXBUtil();
    private static final no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.ObjectFactory SVP_FACTORY_V1 = new no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.ObjectFactory();
    private static final no.nav.vedtak.felles.xml.soeknad.v3.ObjectFactory SØKNAD_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.v3.ObjectFactory();

    @Override
    public MapperEgenskaper mapperEgenskaper() {
        return SVANGERSKAPSPENGER;
    }

    @Override
    public String tilXML(Søknad søknad, AktørId søker, SøknadEgenskap egenskap) {
        return jaxb.marshal(SØKNAD_FACTORY_V3.createSoeknad(tilModell(søknad, søker)));
    }

    @Override
    public String tilXML(Endringssøknad endringssøknad, AktørId søker, SøknadEgenskap egenskap) {
        throw new UnexpectedInputException("Endringssøknad ikke støttet for svangerskapspenger");
    }

    public Soeknad tilModell(Søknad søknad, AktørId søker) {
        var soeknad = new Soeknad();
        soeknad.setSprakvalg(målformFra(søknad.getSøker()));
        soeknad.getAndreVedlegg().addAll(vedleggFra(søknad.getFrivilligeVedlegg()));
        soeknad.getPaakrevdeVedlegg().addAll(vedleggFra(søknad.getPåkrevdeVedlegg()));
        soeknad.setSoeker(søkerFra(søker, søknad.getSøker()));
        soeknad.setOmYtelse(ytelseFra(søknad));
        soeknad.setMottattDato(søknad.getMottattdato());
        soeknad.setTilleggsopplysninger(søknad.getTilleggsopplysninger());
        return soeknad;
    }

    private OmYtelse ytelseFra(Søknad søknad) {
        var ytelse = (no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger) søknad.getYtelse();
        var omYtelse = new OmYtelse();
        omYtelse.getAny().add(jaxb.marshalToElement(svangerskapspengerFra(ytelse)));
        return omYtelse;
    }

    private static JAXBElement<Svangerskapspenger> svangerskapspengerFra(no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger ytelse) {
        var svangerskapspenger = tilSvangerskapspenger(ytelse);
        return SVP_FACTORY_V1.createSvangerskapspenger(svangerskapspenger);
    }

    protected static Svangerskapspenger tilSvangerskapspenger(no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger ytelse) {
        var svangerskapspenger = new Svangerskapspenger();
        svangerskapspenger.setTermindato(ytelse.termindato());
        svangerskapspenger.setFødselsdato(ytelse.fødselsdato());
        svangerskapspenger.setOpptjening(opptjeningFra(ytelse.opptjening()));
        svangerskapspenger.setAvtaltFerieListe(opprettAvtaltFerieListe(ytelse));
        svangerskapspenger.setTilretteleggingListe(tilretteleggingListeFra(ytelse.tilretteleggingbehov()));
        svangerskapspenger.setMedlemskap(medlemsskapFra(ytelse.utenlandsopphold(), relasjonsDatoFra(ytelse.termindato(), ytelse.fødselsdato())));
        return svangerskapspenger;
    }

    private static AvtaltFerieListe opprettAvtaltFerieListe(no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger ytelse) {
        var mappetFerieListe = safeStream(ytelse.avtaltFerie())
                .map(af -> {
                    var avtaltFerie = new AvtaltFerie();
                    Arbeidsgiver arbeidsgiver = mapArbeidsgiver(af);
                    avtaltFerie.setArbeidsgiver(arbeidsgiver);
                    avtaltFerie.setAvtaltFerieFom(af.ferieFom());
                    avtaltFerie.setAvtaltFerieTom(af.ferieTom());
                    return avtaltFerie;
                }).toList();
        var avtaltFerieListe = new AvtaltFerieListe();
        avtaltFerieListe.getAvtaltFerie().addAll(mappetFerieListe);
        return avtaltFerieListe;
    }

    private static Arbeidsgiver mapArbeidsgiver(no.nav.foreldrepenger.common.domain.svangerskapspenger.AvtaltFerie af) {
        return switch (af.arbeidsforhold()) {
            case no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Virksomhet(var orgnr) -> {
                var virksomhetXml = new Virksomhet();
                virksomhetXml.setIdentifikator(orgnr.value());
                yield virksomhetXml;
            }
            case no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.PrivatArbeidsgiver(var fnr) -> {
                var privatArbeidsgiverXml = new PrivatArbeidsgiver();
                privatArbeidsgiverXml.setIdentifikator(fnr.value());
                yield privatArbeidsgiverXml;
            }
            case no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.SelvstendigNæringsdrivende ignored ->
                    throw new IllegalStateException("Oppgitt ferie er ikke støttet for selvstendig næringsdrivende eller frilansere");
            case no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Frilanser frilanserIgnored ->
                    throw new IllegalStateException("Oppgitt ferie er ikke støttet for selvstendig næringsdrivende eller frilansere");
        };
    }

    private static TilretteleggingListe tilretteleggingListeFra(List<Tilretteleggingbehov> tilretteleggingbehov) {
        var tilretteleggingListe = new TilretteleggingListe();
        tilretteleggingListe.getTilrettelegging().addAll(
                safeStream(tilretteleggingbehov)
                .map(V1SvangerskapspengerDomainMapper::tilretteleggingFra)
                .toList());
        return tilretteleggingListe;
    }

    private static Tilrettelegging tilretteleggingFra(Tilretteleggingbehov tilretteleggingbehov) {
        var tilrettelegging = new Tilrettelegging();
        tilrettelegging.setBehovForTilretteleggingFom(tilretteleggingbehov.behovForTilretteleggingFom());
        tilrettelegging.setArbeidsforhold(arbeidsforholdFra(tilretteleggingbehov.arbeidsforhold()));
        tilrettelegging.getVedlegg().addAll(tilretteleggingVedleggFraIDs(tilretteleggingbehov.vedlegg()));

        for (var t : tilretteleggingbehov.tilrettelegginger()) {
            switch (t) {
                case Tilretteleggingbehov.Tilrettelegging.Hel hel -> tilrettelegging.getHelTilrettelegging().add(tilHelTilrettelegging(hel));
                case Tilretteleggingbehov.Tilrettelegging.Delvis del -> tilrettelegging.getDelvisTilrettelegging().add(tilDelTilrettelegging(del));
                case Tilretteleggingbehov.Tilrettelegging.Ingen ingen -> tilrettelegging.getIngenTilrettelegging().add(tilIngenTilrettelegging(ingen));
                default -> throw new UnexpectedInputException("Ukjent tilrettelegging %s", tilrettelegging.getClass().getSimpleName());
            }
        }
        return tilrettelegging;
    }

    private static IngenTilrettelegging tilIngenTilrettelegging(Tilretteleggingbehov.Tilrettelegging.Ingen ingen) {
        var ingenTilrettelegging = new IngenTilrettelegging();
        ingenTilrettelegging.setSlutteArbeidFom(ingen.fom());
        return ingenTilrettelegging;
    }

    private static DelvisTilrettelegging tilDelTilrettelegging(Tilretteleggingbehov.Tilrettelegging.Delvis del) {
        var delvisTilrettelegging = new DelvisTilrettelegging();
        delvisTilrettelegging.setTilrettelagtArbeidFom(del.fom());
        delvisTilrettelegging.setStillingsprosent(BigDecimal.valueOf(del.stillingsprosent()));
        return delvisTilrettelegging;
    }

    private static HelTilrettelegging tilHelTilrettelegging(Tilretteleggingbehov.Tilrettelegging.Hel hel) {
        var helTilrettelegging = new HelTilrettelegging();
        helTilrettelegging.setTilrettelagtArbeidFom(hel.fom());
        return helTilrettelegging;
    }

    private static List<JAXBElement<Object>> tilretteleggingVedleggFraIDs(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(referanse -> SVP_FACTORY_V1.createTilretteleggingVedlegg(tilVedlegg(referanse)))
                .toList();
    }

    private static Arbeidsforhold arbeidsforholdFra(no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Arbeidsforhold forhold) {

        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Virksomhet(var orgnr)) {
            var virksomhet = new Virksomhet();
            virksomhet.setIdentifikator(orgnr.value());
            return virksomhet;
        }
        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.PrivatArbeidsgiver(var fnr)) {
            var privatArbeidsgiver = new PrivatArbeidsgiver();
            privatArbeidsgiver.setIdentifikator(fnr.value());
            return privatArbeidsgiver;
        }

        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.Frilanser(var risikoFaktorer, var tilretteleggingstiltak)) {
            var frilanser = new Frilanser();
            frilanser.setOpplysningerOmTilretteleggingstiltak(tilretteleggingstiltak);
            frilanser.setOpplysningerOmRisikofaktorer(risikoFaktorer);
            return frilanser;
        }

        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.arbeidsforhold.SelvstendigNæringsdrivende(var risikoFaktorer, var tilretteleggingstiltak)) {
            var selvstendigNæringsdrivende = new SelvstendigNæringsdrivende();
            selvstendigNæringsdrivende.setOpplysningerOmTilretteleggingstiltak(tilretteleggingstiltak);
            selvstendigNæringsdrivende.setOpplysningerOmRisikofaktorer(risikoFaktorer);
            return selvstendigNæringsdrivende;
        }

        throw new UnexpectedInputException("Ukjent arbeidsforhold %s", forhold.getClass().getSimpleName());
    }

    private static LocalDate relasjonsDatoFra(LocalDate termindato, LocalDate fødselsdato) {
        return Optional.ofNullable(fødselsdato)
                .orElse(termindato);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [mapperEgenskaper=" + mapperEgenskaper() + "]";
    }

}

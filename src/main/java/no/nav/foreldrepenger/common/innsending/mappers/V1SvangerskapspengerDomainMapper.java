package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.innsending.mappers.MapperEgenskaper.SVANGERSKAPSPENGER;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.medlemsskapFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.målformFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.opptjeningFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.søkerFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.vedleggFra;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.xml.bind.JAXBElement;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Endringssøknad;
import no.nav.foreldrepenger.common.error.UnexpectedInputException;
import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import no.nav.foreldrepenger.common.innsending.mappers.jaxb.SVPV1JAXBUtil;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Vedlegg;
import no.nav.vedtak.felles.xml.soeknad.svangerskapspenger.v1.Arbeidsforhold;
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
        return new Soeknad()
                .withSprakvalg(målformFra(søknad.getSøker()))
                .withAndreVedlegg(vedleggFra(søknad.getFrivilligeVedlegg()))
                .withPaakrevdeVedlegg(vedleggFra(søknad.getPåkrevdeVedlegg()))
                .withSoeker(søkerFra(søker, søknad.getSøker()))
                .withOmYtelse(ytelseFra(søknad))
                .withMottattDato(søknad.getMottattdato())
                .withTilleggsopplysninger(søknad.getTilleggsopplysninger());
    }

    private OmYtelse ytelseFra(Søknad søknad) {
        var ytelse = (no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger) søknad.getYtelse();
        return new OmYtelse().withAny(jaxb.marshalToElement(svangerskapspengerFra(ytelse)));
    }

    private static JAXBElement<Svangerskapspenger> svangerskapspengerFra(
            no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger ytelse) {
        return SVP_FACTORY_V1.createSvangerskapspenger(new Svangerskapspenger()
                .withTermindato(ytelse.termindato())
                .withFødselsdato(ytelse.fødselsdato())
                .withOpptjening(opptjeningFra(ytelse.opptjening()))
                .withTilretteleggingListe(tilretteleggingFra(ytelse.tilrettelegging()))
                .withMedlemskap(medlemsskapFra(ytelse.medlemsskap(),
                        relasjonsDatoFra(ytelse.termindato(), ytelse.fødselsdato()))));
    }

    private static TilretteleggingListe tilretteleggingFra(
            List<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging> tilrettelegginger) {
        return new TilretteleggingListe()
                .withTilrettelegging(
                        tilretteleggingByArbeidsforhold(tilrettelegginger)
                                .values().stream()
                                .map(V1SvangerskapspengerDomainMapper::create)
                                .toList());
    }

    private static Tilrettelegging create(
            List<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging> tiltakListe) {
        var tilrettelegging = new Tilrettelegging();

        for (no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging t : tiltakListe) {
            if (t instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.IngenTilrettelegging i) {
                tilrettelegging.withIngenTilrettelegging(ingenTilretteleggingFra(i));
            } else if (t instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.DelvisTilrettelegging d) {
                tilrettelegging.withDelvisTilrettelegging(delvisTilretteleggingFra(d));
            } else if (t instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.HelTilrettelegging h) {
                tilrettelegging.withHelTilrettelegging(helTilretteleggingFra(h));
            } else {
                throw new UnexpectedInputException("Ukjent tilrettelegging %s",
                        tilrettelegging.getClass().getSimpleName());
            }
        }

        tiltakListe.stream()
                .findAny()
                .ifPresent(b -> {
                    tilrettelegging.withBehovForTilretteleggingFom(b.getBehovForTilretteleggingFom());
                    tilrettelegging.withVedlegg(tilretteleggingVedleggFraIDs(b.getVedlegg()));
                    tilrettelegging.withArbeidsforhold(arbeidsforholdFra(b.getArbeidsforhold()));
                });

        return tilrettelegging;
    }

    private static IngenTilrettelegging ingenTilretteleggingFra(
            no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.IngenTilrettelegging ingen) {
        return new IngenTilrettelegging()
                .withSlutteArbeidFom(ingen.getSlutteArbeidFom());
    }

    private static DelvisTilrettelegging delvisTilretteleggingFra(
            no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.DelvisTilrettelegging delvis) {

        return new DelvisTilrettelegging()
                .withTilrettelagtArbeidFom(delvis.getTilrettelagtArbeidFom())
                .withStillingsprosent(BigDecimal.valueOf(prosentFra(delvis.getStillingsprosent())));
    }

    private static HelTilrettelegging helTilretteleggingFra(
            no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.HelTilrettelegging hel) {
        return new HelTilrettelegging()
                .withTilrettelagtArbeidFom(hel.getTilrettelagtArbeidFom());
    }

    private static double prosentFra(ProsentAndel prosent) {
        return Optional.ofNullable(prosent)
                .map(ProsentAndel::prosent)
                .orElse(0d);
    }

    private static List<JAXBElement<Object>> tilretteleggingVedleggFraIDs(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(s -> SVP_FACTORY_V1.createTilretteleggingVedlegg(new Vedlegg().withId(s)))
                .toList();
    }

    private static Arbeidsforhold arbeidsforholdFra(
            no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold forhold) {

        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Virksomhet virksomhet) {
            return new Virksomhet()
                    .withIdentifikator(virksomhet.orgnr().value());
        }
        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.PrivatArbeidsgiver privat) {
            return new PrivatArbeidsgiver()
                    .withIdentifikator(privat.fnr().value());
        }

        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Frilanser frilanser) {
            return new Frilanser()
                    .withOpplysningerOmTilretteleggingstiltak(frilanser.tilretteleggingstiltak())
                    .withOpplysningerOmRisikofaktorer(frilanser.risikoFaktorer());
        }

        if (forhold instanceof no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.SelvstendigNæringsdrivende selvstendig) {
            return new SelvstendigNæringsdrivende()
                    .withOpplysningerOmTilretteleggingstiltak(selvstendig.tilretteleggingstiltak())
                    .withOpplysningerOmRisikofaktorer(selvstendig.risikoFaktorer());
        }

        throw new UnexpectedInputException("Ukjent arbeidsforhold %s", forhold.getClass().getSimpleName());
    }

    private static LocalDate relasjonsDatoFra(LocalDate termindato, LocalDate fødselsdato) {
        return Optional.ofNullable(fødselsdato)
                .orElse(termindato);
    }

    private static Map<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold, List<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging>> tilretteleggingByArbeidsforhold(
            List<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging> tilretteleggingsPerioder) {
        Map<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold, List<no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging>> tilretteleggingByArbeidsforhold = new HashMap<>();
        tilretteleggingsPerioder.forEach(tp -> tilretteleggingByArbeidsforhold
                .computeIfAbsent(tp.getArbeidsforhold(), key -> new ArrayList<>())
                .add(tp));
        return tilretteleggingByArbeidsforhold;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [mapperEgenskaper=" + mapperEgenskaper() + "]";
    }

}

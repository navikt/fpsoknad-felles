package no.nav.foreldrepenger.common.innsending.mappers;

import static no.nav.foreldrepenger.common.innsending.mappers.MapperEgenskaper.FORELDREPENGER;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.landFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.medlemsskapFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.målformFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.opptjeningFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.søkerFra;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.tilVedlegg;
import static no.nav.foreldrepenger.common.innsending.mappers.V3DomainMapperCommon.vedleggFra;
import static no.nav.foreldrepenger.common.util.LangUtil.toBoolean;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.xml.bind.JAXBElement;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UtenlandskForelder;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Omsorgsovertakelse;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Endringssøknad;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.FriUtsettelsesPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.GradertUttaksPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.LukketPeriodeMedVedlegg;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.MorsAktivitet;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.OppholdsPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Oppholdsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.OverføringsPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Overføringsårsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.UtsettelsesPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.UtsettelsesÅrsak;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.UttaksPeriode;
import no.nav.foreldrepenger.common.error.UnexpectedInputException;
import no.nav.foreldrepenger.common.innsending.SøknadEgenskap;
import no.nav.foreldrepenger.common.innsending.mappers.jaxb.FPV3JAXBUtil;
import no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v3.Endringssoeknad;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.AnnenForelder;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.AnnenForelderMedNorskIdent;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.AnnenForelderUtenNorskIdent;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Foedsel;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Rettigheter;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.SoekersRelasjonTilBarnet;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Termin;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.UkjentForelder;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Dekningsgrad;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Foreldrepenger;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Dekningsgrader;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.MorsAktivitetsTyper;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Omsorgsovertakelseaarsaker;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Oppholdsaarsaker;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Overfoeringsaarsaker;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Utsettelsesaarsaker;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Uttaksperiodetyper;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Arbeidsgiver;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Fordeling;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Gradering;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Oppholdsperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Overfoeringsperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Person;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Utsettelsesperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Uttaksperiode;
import no.nav.vedtak.felles.xml.soeknad.uttak.v3.Virksomhet;
import no.nav.vedtak.felles.xml.soeknad.v3.OmYtelse;
import no.nav.vedtak.felles.xml.soeknad.v3.Soeknad;

public class V3ForeldrepengerDomainMapper implements DomainMapper {
    private static final FPV3JAXBUtil JAXB = new FPV3JAXBUtil();
    private static final String UKJENT_KODEVERKSVERDI = "-";
    private static final no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.ObjectFactory FP_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.ObjectFactory();
    private static final no.nav.vedtak.felles.xml.soeknad.felles.v3.ObjectFactory FELLES_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.felles.v3.ObjectFactory();
    private static final no.nav.vedtak.felles.xml.soeknad.v3.ObjectFactory SØKNAD_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.v3.ObjectFactory();
    private static final no.nav.vedtak.felles.xml.soeknad.uttak.v3.ObjectFactory UTTAK_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.uttak.v3.ObjectFactory();
    private static final no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v3.ObjectFactory ENDRING_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.endringssoeknad.v3.ObjectFactory();

    private final AktørIdTilFnrConverter aktørIdTilFnrConverter;

    public V3ForeldrepengerDomainMapper(AktørIdTilFnrConverter aktørIdTilFnrConverter) {
        this.aktørIdTilFnrConverter = aktørIdTilFnrConverter;
    }

    @Override
    public MapperEgenskaper mapperEgenskaper() {
        return FORELDREPENGER;
    }

    @Override
    public String tilXML(Søknad søknad, AktørId søker, SøknadEgenskap egenskap) {
        return JAXB.marshal(SØKNAD_FACTORY_V3.createSoeknad(tilModell(søknad, søker)));
    }

    @Override
    public String tilXML(Endringssøknad endringssøknad, AktørId søker, SøknadEgenskap egenskap) {
        return JAXB.marshal(SØKNAD_FACTORY_V3.createSoeknad(tilModell(endringssøknad, søker)));
    }

    protected Soeknad tilModell(Søknad søknad, AktørId søker) {
        var soeknad = new Soeknad();
        soeknad.setSprakvalg(målformFra(søknad.getSøker()));
        soeknad.getAndreVedlegg().addAll(vedleggFra(søknad.getFrivilligeVedlegg()));
        soeknad.getPaakrevdeVedlegg().addAll(vedleggFra(søknad.getPåkrevdeVedlegg()));
        soeknad.setSoeker(søkerFra(søker, søknad.getSøker()));
        soeknad.setOmYtelse(ytelseFraSøknad(søknad));
        soeknad.setMottattDato(søknad.getMottattdato());
        soeknad.setTilleggsopplysninger(søknad.getTilleggsopplysninger());
        return soeknad;
    }

    private static Soeknad tilModell(Endringssøknad endringsøknad, AktørId søker) {
        var soeknad = new Soeknad();
        soeknad.setSprakvalg(målformFra(endringsøknad.getSøker()));
        soeknad.getAndreVedlegg().addAll(vedleggFra(endringsøknad.getFrivilligeVedlegg()));
        soeknad.getPaakrevdeVedlegg().addAll(vedleggFra(endringsøknad.getPåkrevdeVedlegg()));
        soeknad.setSoeker(søkerFra(søker, endringsøknad.getSøker()));
        soeknad.setOmYtelse(ytelseFraEndringssøknad(endringsøknad));
        soeknad.setMottattDato(endringsøknad.getMottattdato());
        return soeknad;
    }

    private static JAXBElement<Endringssoeknad> endringssøknadFra(Endringssøknad endringssøknad) {
        var endringssoeknad = new Endringssoeknad();
        endringssoeknad.setFordeling(fordelingFra(endringssøknad));
        endringssoeknad.setSaksnummer(endringssøknad.getSaksnr().value());
        return ENDRING_FACTORY_V3.createEndringssoeknad(endringssoeknad);
    }

    private JAXBElement<Foreldrepenger> foreldrepengerFra(no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger ytelse) {
        return FP_FACTORY_V3.createForeldrepenger(tilForeldrepenger(ytelse));
    }

    protected Foreldrepenger tilForeldrepenger(no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger ytelse) {
        var foreldrepenger = new Foreldrepenger();
        foreldrepenger.setDekningsgrad(dekningsgradFra(ytelse.dekningsgrad()));
        foreldrepenger.setMedlemskap(medlemsskapFra(ytelse.medlemsskap(), ytelse.utenlandsopphold(), ytelse.relasjonTilBarn().relasjonsDato()));
        foreldrepenger.setOpptjening(opptjeningFra(ytelse.opptjening()));
        foreldrepenger.setFordeling(fordelingFra(ytelse.fordeling()));
        foreldrepenger.setRettigheter(rettigheterFra(ytelse.rettigheter(), erAnnenForelderUkjent(ytelse.annenForelder())));
        foreldrepenger.setAnnenForelder(annenForelderFra(ytelse.annenForelder()));
        foreldrepenger.setRelasjonTilBarnet(relasjonFra(ytelse.relasjonTilBarn()));
        return foreldrepenger;
    }

    private static OmYtelse ytelseFraEndringssøknad(Endringssøknad endringssøknad) {
        var omYtelse = new OmYtelse();
        omYtelse.getAny().add(endringssøknadFra(endringssøknad));
        return omYtelse;
    }

    private OmYtelse ytelseFraSøknad(Søknad søknad) {
        var omYtelse = new OmYtelse();
        omYtelse.getAny().add(JAXB.marshalToElement(foreldrepengerFra((no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger) søknad.getYtelse())));
        return omYtelse;
    }

    private static Fordeling fordelingFra(Endringssøknad endringssøknad) {
        return fordelingFra(
                ((no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger) endringssøknad.getYtelse()).fordeling()
        );
    }

    private static boolean erAnnenForelderUkjent(no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder annenForelder) {
        return annenForelder instanceof no.nav.foreldrepenger.common.domain.felles.annenforelder.UkjentForelder;
    }

    private static Dekningsgrad dekningsgradFra(no.nav.foreldrepenger.common.domain.foreldrepenger.Dekningsgrad dekningsgrad) {
        return Optional.ofNullable(dekningsgrad)
                .map(no.nav.foreldrepenger.common.domain.foreldrepenger.Dekningsgrad::kode)
                .map(V3ForeldrepengerDomainMapper::dekningsgradFra)
                .map(V3ForeldrepengerDomainMapper::tilDekningsgrad)
                .orElse(null);
    }

    private static Dekningsgrad tilDekningsgrad(Dekningsgrader d) {
        var dekningsgrad = new Dekningsgrad();
        dekningsgrad.setDekningsgrad(d);
        return dekningsgrad;
    }

    private static Dekningsgrader dekningsgradFra(Integer kode) {
        var dekningsgrad = new Dekningsgrader();
        dekningsgrad.setKode(String.valueOf(kode));
        dekningsgrad.setKodeverk(dekningsgrad.getKodeverk());
        return dekningsgrad;
    }

    private static Fordeling fordelingFra(no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling fordeling) {
        return Optional.ofNullable(fordeling)
                .map(V3ForeldrepengerDomainMapper::create)
                .orElse(null);
    }

    private static Fordeling create(no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling fordeling) {
        var fordelingXML = new Fordeling();
        fordelingXML.getPerioder().addAll(perioderFra(fordeling.perioder()));
        fordelingXML.setOenskerJustertVedFoedsel(fordeling.ønskerJustertUttakVedFødsel());
        fordelingXML.setOenskerKvoteOverfoert(overføringsÅrsakFra(UKJENT_KODEVERKSVERDI));
        fordelingXML.setAnnenForelderErInformert(toBoolean(fordeling.erAnnenForelderInformert()));
        return fordelingXML;
    }

    private static List<no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg> perioderFra(
            List<LukketPeriodeMedVedlegg> perioder) {
        return safeStream(perioder)
                .map(V3ForeldrepengerDomainMapper::lukketPeriodeFra)
                .toList();
    }

    private static List<JAXBElement<Object>> lukketPeriodeVedleggFra(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(referanse -> UTTAK_FACTORY_V3.createLukketPeriodeMedVedleggVedlegg(tilVedlegg(referanse)))
                .toList();
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg lukketPeriodeFra(LukketPeriodeMedVedlegg p) {
        return Optional.ofNullable(p)
                .map(V3ForeldrepengerDomainMapper::create)
                .orElse(null);
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg create(
            LukketPeriodeMedVedlegg periode) {
        if (periode instanceof OverføringsPeriode p) {
            return createOverføringsperiode(p);
        }
        if (periode instanceof OppholdsPeriode p) {
            return createOppholsperiode(p);
        }
        if (periode instanceof FriUtsettelsesPeriode p) {
            return createFriUtsettelsesPeriode(p);
        }
        if (periode instanceof UtsettelsesPeriode p) {
            return createUtsettelsesperiode(p);
        }
        if (periode instanceof GradertUttaksPeriode p) {
            return createGradertUttaksperiode(p);
        }

        if (periode instanceof UttaksPeriode p) {
            return createUttaksperiode(p);
        }
        throw new UnexpectedInputException("Ukjent periode " + periode.getClass().getSimpleName());
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg createOverføringsperiode(OverføringsPeriode o) {
        var overfoeringsperiode = new Overfoeringsperiode();
        overfoeringsperiode.setFom(o.getFom());
        overfoeringsperiode.setTom(o.getTom());
        overfoeringsperiode.setOverfoeringAv(uttaksperiodeTypeFra(o.getUttaksperiodeType()));
        overfoeringsperiode.setAarsak(påkrevdOverføringsÅrsakFra(o.getÅrsak()));
        overfoeringsperiode.getVedlegg().addAll(lukketPeriodeVedleggFra(o.getVedlegg()));
        return overfoeringsperiode;
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg createOppholsperiode(OppholdsPeriode o) {
        var oppholdsperiode = new Oppholdsperiode();
        oppholdsperiode.setFom(o.getFom());
        oppholdsperiode.setTom(o.getTom());
        oppholdsperiode.setAarsak(oppholdsÅrsakFra(o.getÅrsak()));
        oppholdsperiode.getVedlegg().addAll(lukketPeriodeVedleggFra(o.getVedlegg()));
        return oppholdsperiode;
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg createUtsettelsesperiode(UtsettelsesPeriode u) {
        var utsettelsesperiode = new Utsettelsesperiode();
        utsettelsesperiode.setFom(u.getFom());
        utsettelsesperiode.setTom(u.getTom());
        utsettelsesperiode.setErArbeidstaker(u.isErArbeidstaker());
        utsettelsesperiode.setMorsAktivitetIPerioden(morsAktivitetFra(u.getMorsAktivitetsType()));
        utsettelsesperiode.setAarsak(utsettelsesÅrsakFra(u.getÅrsak()));
        utsettelsesperiode.getVedlegg().addAll(lukketPeriodeVedleggFra(u.getVedlegg()));
        return utsettelsesperiode;
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg createFriUtsettelsesPeriode(FriUtsettelsesPeriode p) {
        var utsettelsesperiode = new Utsettelsesperiode();
        utsettelsesperiode.setFom(p.getFom());
        utsettelsesperiode.setTom(p.getTom());
        utsettelsesperiode.setMorsAktivitetIPerioden(morsAktivitetFra(p.getMorsAktivitetsType(), true));
        utsettelsesperiode.setAarsak(utsettelsesÅrsakFra(p.getÅrsak()));
        utsettelsesperiode.getVedlegg().addAll(lukketPeriodeVedleggFra(p.getVedlegg()));
        return utsettelsesperiode;
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg createGradertUttaksperiode(GradertUttaksPeriode g) {
        var gradering = new Gradering();
        gradering.setFom(g.getFom());
        gradering.setTom(g.getTom());
        gradering.setType(uttaksperiodeTypeFra(g.getUttaksperiodeType()));
        gradering.setOenskerSamtidigUttak(g.isØnskerSamtidigUttak());
        gradering.setMorsAktivitetIPerioden(morsAktivitetFra(g.getMorsAktivitetsType()));
        gradering.setOenskerFlerbarnsdager(g.isØnskerFlerbarnsdager());
        gradering.setErArbeidstaker(g.isErArbeidstaker());
        gradering.setArbeidtidProsent(prosentFra(g.getArbeidstidProsent()));
        gradering.setArbeidsgiver(arbeidsgiverFra(g.getVirksomhetsnummer()));
        gradering.setArbeidsforholdSomSkalGraderes(g.isArbeidsForholdSomskalGraderes());
        gradering.getVedlegg().addAll(lukketPeriodeVedleggFra(g.getVedlegg()));
        Optional.ofNullable(g.getFrilans()).ifPresent(gradering::setErFrilanser);
        Optional.ofNullable(g.getSelvstendig()).ifPresent(gradering::setErSelvstNæringsdrivende);

        if (g.isØnskerSamtidigUttak()) {
            gradering.setSamtidigUttakProsent(prosentFra(g.getSamtidigUttakProsent()));
        }

        return gradering;
    }

    private static no.nav.vedtak.felles.xml.soeknad.uttak.v3.LukketPeriodeMedVedlegg createUttaksperiode(UttaksPeriode u) {
        var uttaksperiode = new Uttaksperiode();
        uttaksperiode.setFom(u.getFom());
        uttaksperiode.setTom(u.getTom());
        uttaksperiode.setSamtidigUttakProsent(prosentFra(u.getSamtidigUttakProsent()));
        uttaksperiode.setOenskerFlerbarnsdager(u.isØnskerFlerbarnsdager());
        uttaksperiode.setType(uttaksperiodeTypeFra(u.getUttaksperiodeType()));
        uttaksperiode.setOenskerSamtidigUttak(u.isØnskerSamtidigUttak());
        uttaksperiode.setMorsAktivitetIPerioden(morsAktivitetFra(u.getMorsAktivitetsType()));
        uttaksperiode.getVedlegg().addAll(lukketPeriodeVedleggFra(u.getVedlegg()));
        return uttaksperiode;
    }

    private static double prosentFra(ProsentAndel prosent) {
        return Optional.ofNullable(prosent)
                .map(ProsentAndel::prosent)
                .orElse(0d);
    }

    private static Arbeidsgiver arbeidsgiverFra(List<String> arbeidsgiver) {
        if (arbeidsgiver == null || arbeidsgiver.isEmpty()) {
            return null;
        }
        return Optional.ofNullable(arbeidsgiver.get(0))
                .map(V3ForeldrepengerDomainMapper::arbeidsgiverFra)
                .orElse(null);
    }

    private static Arbeidsgiver arbeidsgiverFra(String id) {
        return switch (id.length()) {
            case 11 -> tilPerson(id);
            case 9 -> tilArbeidsgiver(id);
            default -> throw new UnexpectedInputException("Ugyldig lengde " + id.length() + " for arbeidsgiver");
        };
    }

    private static Arbeidsgiver tilArbeidsgiver(String orgnummer) {
        var virksomhet = new Virksomhet();
        virksomhet.setIdentifikator(orgnummer);
        return virksomhet;
    }

    private static Arbeidsgiver tilPerson(String fnr) {
        var person = new Person();
        person.setIdentifikator(fnr);
        return person;
    }

    private static Uttaksperiodetyper uttaksperiodeTypeFra(StønadskontoType type) {
        return Optional.ofNullable(type)
                .map(StønadskontoType::name)
                .map(V3ForeldrepengerDomainMapper::uttaksperiodeTypeFra)
                .orElseThrow(() -> new UnexpectedInputException("Stønadskontotype må være satt for uttaksperiodetype" + type));
    }

    private static Uttaksperiodetyper uttaksperiodeTypeFra(String type) {
        var periodeType = new Uttaksperiodetyper();
        periodeType.setKode(type);
        periodeType.setKodeverk(periodeType.getKodeverk());
        return periodeType;
    }

    private static MorsAktivitetsTyper morsAktivitetFra(MorsAktivitet aktivitet) {
        return morsAktivitetFra(aktivitet, false);
    }

    private static MorsAktivitetsTyper morsAktivitetFra(MorsAktivitet aktivitet, boolean optional) {
        if (optional) {
            return Optional.ofNullable(aktivitet)
                    .map(MorsAktivitet::name)
                    .map(V3ForeldrepengerDomainMapper::morsAktivitetFra)
                    .orElse(null);
        }
        return Optional.ofNullable(aktivitet)
                .map(MorsAktivitet::name)
                .map(V3ForeldrepengerDomainMapper::morsAktivitetFra)
                .orElse(morsAktivitetFra(UKJENT_KODEVERKSVERDI));
    }

    private static MorsAktivitetsTyper morsAktivitetFra(String aktivitet) {
        var morsAktivitet = new MorsAktivitetsTyper();
        morsAktivitet.setKode(aktivitet);
        morsAktivitet.setKodeverk(morsAktivitet.getKodeverk()); // TODO
        return morsAktivitet;
    }

    private static Utsettelsesaarsaker utsettelsesÅrsakFra(UtsettelsesÅrsak årsak) {
        return Optional.ofNullable(årsak)
                .map(UtsettelsesÅrsak::name)
                .map(V3ForeldrepengerDomainMapper::utsettelsesÅrsakFra)
                .orElse(null);
    }

    private static Utsettelsesaarsaker utsettelsesÅrsakFra(String årsak) {
        var utsettelsesÅrsak = new Utsettelsesaarsaker();
        utsettelsesÅrsak.setKode(årsak);
        utsettelsesÅrsak.setKodeverk(utsettelsesÅrsak.getKodeverk());
        return utsettelsesÅrsak;
    }

    private static Oppholdsaarsaker oppholdsÅrsakFra(Oppholdsårsak årsak) {
        return Optional.ofNullable(årsak)
                .map(Oppholdsårsak::name)
                .map(V3ForeldrepengerDomainMapper::oppholdsÅrsakFra)
                .orElseThrow(() -> new UnexpectedInputException("Oppholdsårsak må være satt"));
    }

    private static Oppholdsaarsaker oppholdsÅrsakFra(String årsak) {
        var oppholdsÅrsak = new Oppholdsaarsaker();
        oppholdsÅrsak.setKode(årsak);
        oppholdsÅrsak.setKodeverk(oppholdsÅrsak.getKodeverk());
        return oppholdsÅrsak;
    }

    private static Overfoeringsaarsaker påkrevdOverføringsÅrsakFra(Overføringsårsak årsak) {
        return Optional.ofNullable(årsak)
                .map(Overføringsårsak::name)
                .map(V3ForeldrepengerDomainMapper::overføringsÅrsakFra)
                .orElseThrow(() -> new UnexpectedInputException("Oppholdsårsak må være satt"));
    }

    private static Overfoeringsaarsaker overføringsÅrsakFra(String årsak) {
        var overføringsÅrsak = new Overfoeringsaarsaker();
        overføringsÅrsak.setKode(årsak);
        overføringsÅrsak.setKodeverk(overføringsÅrsak.getKodeverk());
        return overføringsÅrsak;
    }

    private static Rettigheter rettigheterFra(no.nav.foreldrepenger.common.domain.foreldrepenger.Rettigheter r, boolean ukjentForelder) {
        if (ukjentForelder) {
            return rettigheterForUkjentForelder();
        }
        return Optional.ofNullable(r)
                .map(V3ForeldrepengerDomainMapper::create)
                .orElse(null);
    }

    private static Rettigheter rettigheterForUkjentForelder() {
        var rettigheter = new Rettigheter();
        rettigheter.setHarOmsorgForBarnetIPeriodene(true);
        rettigheter.setHarAnnenForelderRett(false);
        rettigheter.setHarAleneomsorgForBarnet(true);
        return rettigheter;
    }

    private static Rettigheter create(no.nav.foreldrepenger.common.domain.foreldrepenger.Rettigheter r) {
        var rettigheter = new Rettigheter();
        rettigheter.setHarOmsorgForBarnetIPeriodene(true);
        rettigheter.setHarAnnenForelderRett(r.harAnnenForelderRett());
        rettigheter.setHarAleneomsorgForBarnet(toBoolean(r.harAleneOmsorgForBarnet()));
        rettigheter.setHarMorUforetrygd(toBoolean(r.harMorUføretrygd()));
        rettigheter.setHarAnnenForelderOppholdtSegIEOS(r.harAnnenForelderOppholdtSegIEØS());
        rettigheter.setHarAnnenForelderTilsvarendeRettEOS(toBoolean(r.harAnnenForelderTilsvarendeRettEØS()));
        return rettigheter;
    }

    private AnnenForelder annenForelderFra(
            no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder annenForelder) {
        if (erAnnenForelderUkjent(annenForelder)) {
            return new UkjentForelder();
        }
        if (annenForelder instanceof UtenlandskForelder u) {
            return utenlandskForelder(u);
        }
        if (annenForelder instanceof NorskForelder n) {
            return norskForelder(n);
        }
        return null;
    }

    private static AnnenForelderUtenNorskIdent utenlandskForelder(UtenlandskForelder utenlandskForelder) {
        var annenForelderUtenNorskIdent = new AnnenForelderUtenNorskIdent();
        annenForelderUtenNorskIdent.setUtenlandskPersonidentifikator(utenlandskForelder.id());
        annenForelderUtenNorskIdent.setLand(landFra(utenlandskForelder.land()));
        return annenForelderUtenNorskIdent;
    }

    private AnnenForelderMedNorskIdent norskForelder(NorskForelder norskForelder) {
        var annenForelderMedNorskIdent = new AnnenForelderMedNorskIdent();
        annenForelderMedNorskIdent.setAktoerId(aktørIdTilFnrConverter.konverter(norskForelder.fnr()).value());
        return annenForelderMedNorskIdent;
    }

    private static SoekersRelasjonTilBarnet relasjonFra(RelasjonTilBarn relasjonTilBarn) {
        return Optional.ofNullable(relasjonTilBarn)
                .map(V3ForeldrepengerDomainMapper::create)
                .orElse(null);
    }

    private static SoekersRelasjonTilBarnet create(RelasjonTilBarn relasjonTilBarn) {
        if (relasjonTilBarn instanceof Fødsel f) {
            return createFødsel(f);
        }
        if (relasjonTilBarn instanceof FremtidigFødsel f) {
            return createTermin(f);
        }
        if (relasjonTilBarn instanceof Adopsjon a) {
            return createAdopsjon(a);
        }
        if (relasjonTilBarn instanceof Omsorgsovertakelse o) {
            return createOmsorgsovertakelse(o);
        }
        throw new UnexpectedInputException(
                "Relasjon " + relasjonTilBarn.getClass().getSimpleName() + " er ikke støttet");
    }

    private static SoekersRelasjonTilBarnet createOmsorgsovertakelse(Omsorgsovertakelse omsorgsovertakelse) {
        var omsorgsovertakelseXLM = new no.nav.vedtak.felles.xml.soeknad.felles.v3.Omsorgsovertakelse();
        omsorgsovertakelseXLM.getVedlegg().addAll(relasjonTilBarnVedleggFra(omsorgsovertakelse.getVedlegg()));
        omsorgsovertakelseXLM.setAntallBarn(omsorgsovertakelse.getAntallBarn());
        omsorgsovertakelseXLM.getFoedselsdato().addAll(omsorgsovertakelse.getFødselsdato());
        omsorgsovertakelseXLM.setOmsorgsovertakelsesdato(omsorgsovertakelse.getOmsorgsovertakelsesdato());
        var omsorgsovertakelseaarsaker = new Omsorgsovertakelseaarsaker();
        omsorgsovertakelseaarsaker.setKode("OVERTATT_OMSORG");
        omsorgsovertakelseXLM.setOmsorgsovertakelseaarsak(omsorgsovertakelseaarsaker);
        omsorgsovertakelseXLM.setBeskrivelse("Omsorgsovertakelse");
        return omsorgsovertakelseXLM;
    }

    private static SoekersRelasjonTilBarnet createFødsel(Fødsel fødsel) {
        var foedsel = new Foedsel();
        foedsel.getVedlegg().addAll(relasjonTilBarnVedleggFra(fødsel.getVedlegg()));
        foedsel.setFoedselsdato(fødsel.getFødselsdato().get(0));
        foedsel.setTermindato(fødsel.getTermindato());
        foedsel.setAntallBarn(fødsel.getAntallBarn());
        return foedsel;
    }

    private static SoekersRelasjonTilBarnet createTermin(FremtidigFødsel termin) {
        var terminXML = new Termin();
        terminXML.getVedlegg().addAll(relasjonTilBarnVedleggFra(termin.getVedlegg()));
        terminXML.setAntallBarn(termin.getAntallBarn());
        terminXML.setTermindato(termin.getTerminDato());
        terminXML.setUtstedtdato(termin.getUtstedtDato());
        return terminXML;
    }

    private static SoekersRelasjonTilBarnet createAdopsjon(Adopsjon adopsjon) {
        var adopsjonXML = new no.nav.vedtak.felles.xml.soeknad.felles.v3.Adopsjon();
        adopsjonXML.getVedlegg().addAll(relasjonTilBarnVedleggFra(adopsjon.getVedlegg()));
        adopsjonXML.setAntallBarn(adopsjon.getAntallBarn());
        adopsjonXML.getFoedselsdato().addAll(adopsjon.getFødselsdato());
        adopsjonXML.setOmsorgsovertakelsesdato(adopsjon.getOmsorgsovertakelsesdato());
        adopsjonXML.setAdopsjonAvEktefellesBarn(adopsjon.isEktefellesBarn());
        adopsjonXML.setAnkomstdato(adopsjon.getAnkomstDato());
        return adopsjonXML;
    }

    private static List<JAXBElement<Object>> relasjonTilBarnVedleggFra(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(referanse -> FELLES_FACTORY_V3.createSoekersRelasjonTilBarnetVedlegg(tilVedlegg(referanse)))
                .toList();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [oppslag=" + aktørIdTilFnrConverter + ", mapperEgenskaper=" + mapperEgenskaper() + "]";
    }
}

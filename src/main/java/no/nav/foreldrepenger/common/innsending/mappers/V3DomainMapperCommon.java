package no.nav.foreldrepenger.common.innsending.mappers;

import com.neovisionaries.i18n.CountryCode;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.BrukerRolle;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.felles.InnsendingsType;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.opptjening.*;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;
import no.nav.foreldrepenger.common.error.UnexpectedInputException;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.*;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.AnnenOpptjening;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Frilans;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.NorskOrganisasjon;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Opptjening;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.UtenlandskArbeidsforhold;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.UtenlandskOrganisasjon;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.*;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.neovisionaries.i18n.CountryCode.XK;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.LASTET_OPP;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.SEND_SENERE;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

final class V3DomainMapperCommon {
    private static final Logger LOG = LoggerFactory.getLogger(V3DomainMapperCommon.class);
    private static final Land KOSOVO = landFra("XXK");
    private static final no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.ObjectFactory FP_FACTORY_V3 = new no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.ObjectFactory();

    private V3DomainMapperCommon() {

    }

    static Spraakkode målformFra(Søker søker) {
        LOG.trace("Mapper målform fra {}", søker.målform());
        return Optional.ofNullable(søker)
                .map(Søker::målform)
                .map(Målform::name)
                .map(V3DomainMapperCommon::målformFra)
                .orElse(målformFra(Målform.standard()));
    }

    static Periode periodeFra(ÅpenPeriode periode) {
        return Optional.ofNullable(periode)
                .map(p -> new Periode()
                        .withFom(p.fom())
                        .withTom(p.tom()))
                .orElse(null);
    }

    static Opptjening opptjeningFra(
            no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening opptjening) {
        return new Opptjening()
                .withUtenlandskArbeidsforhold(utenlandskeArbeidsforholdFra(opptjening.utenlandskArbeidsforhold()))
                .withFrilans(frilansFra(opptjening.frilans()))
                .withEgenNaering(egneNæringerFra(opptjening.egenNæring()))
                .withAnnenOpptjening(andreOpptjeningerFra(opptjening.annenOpptjening()));
    }

    static Medlemskap medlemsskapFra(Medlemsskap ms, LocalDate relasjonsDato) {
        return Optional.ofNullable(ms)
                .map(m -> create(m, relasjonsDato))
                .orElse(null);
    }

    private static Medlemskap create(Medlemsskap ms, LocalDate relasjonsDato) {
        return new Medlemskap()
                .withOppholdUtlandet(oppholdUtlandetFra(ms))
                .withINorgeVedFoedselstidspunkt(ms.varINorge(relasjonsDato))
                .withBoddINorgeSiste12Mnd(oppholdINorgeSiste12(ms))
                .withBorINorgeNeste12Mnd(oppholdINorgeNeste12(ms));
    }

    private static boolean oppholdINorgeSiste12(Medlemsskap ms) {
        return ms.tidligereUtenlandsopphold().isEmpty();
    }

    private static boolean oppholdINorgeNeste12(Medlemsskap ms) {
        return ms.framtidigUtenlandsopphold().isEmpty();
    }

    private static List<OppholdUtlandet> oppholdUtlandetFra(Medlemsskap ms) {
        return safeStream(ms.utenlandsOpphold())
                .map(V3DomainMapperCommon::utenlandOppholdFra)
                .toList();
    }

    private static OppholdUtlandet utenlandOppholdFra(Utenlandsopphold opphold) {
        return Optional.ofNullable(opphold)
                .map(o -> new OppholdUtlandet()
                        .withPeriode(new Periode()
                                .withFom(o.fom())
                                .withTom(o.tom()))
                        .withLand(landFra(o.land())))
                .orElse(null);
    }

    private static List<Virksomhetstyper> virksomhetsTyperFra(List<Virksomhetstype> typer) {
        return safeStream(typer)
                .map(V3DomainMapperCommon::virksomhetsTypeFra)
                .toList();
    }

    private static Virksomhetstyper virksomhetsTypeFra(Virksomhetstype type) {
        return Optional.ofNullable(type)
                .map(Virksomhetstype::name)
                .map(V3DomainMapperCommon::virksomhetsTypeFra)
                .orElse(null);
    }

    private static Virksomhetstyper virksomhetsTypeFra(String type) {
        var vt = new Virksomhetstyper().withKode(type);
        vt.setKodeverk(vt.getKodeverk());
        return vt;
    }

    private static List<EgenNaering> egneNæringerFra(List<EgenNæring> egenNæring) {
        return safeStream(egenNæring)
                .map(V3DomainMapperCommon::egenNæringFra)
                .toList();
    }

    private static EgenNaering egenNæringFra(EgenNæring egenNæring) {
        return Optional.ofNullable(egenNæring)
                .map(V3DomainMapperCommon::create)
                .orElse(null);
    }

    private static EgenNaering create(EgenNæring egenNæring) {
        if (CountryCode.NO.equals(egenNæring.registrertILand()) || egenNæring.registrertILand() == null) {
            return norskOrganisasjon(egenNæring);
        } else {
            return utenlandskOrganisasjon(egenNæring);
        }
    }

    private static UtenlandskOrganisasjon utenlandskOrganisasjon(EgenNæring utenlandskOrg) {
        return new UtenlandskOrganisasjon()
                .withVedlegg(egenNæringVedleggFraIDs(utenlandskOrg.vedlegg()))
                .withBeskrivelseAvEndring(utenlandskOrg.beskrivelseEndring())
                .withNaerRelasjon(utenlandskOrg.nærRelasjon())
                .withEndringsDato(utenlandskOrg.endringsDato())
                .withOppstartsdato(utenlandskOrg.oppstartsDato())
                .withErNyoppstartet(utenlandskOrg.erNyOpprettet())
                .withErNyIArbeidslivet(utenlandskOrg.erNyIArbeidslivet())
                .withErVarigEndring(utenlandskOrg.erVarigEndring())
                .withNaeringsinntektBrutto(BigInteger.valueOf(utenlandskOrg.næringsinntektBrutto()))
                .withNavn(utenlandskOrg.orgName())
                .withRegistrertILand(landFra(utenlandskOrg.registrertILand()))
                .withPeriode(periodeFra(utenlandskOrg.periode()))
                .withRegnskapsfoerer(regnskapsFørerFra(utenlandskOrg.regnskapsførere()))
                .withVirksomhetstype(virksomhetsTyperFra(utenlandskOrg.virksomhetsTyper()));
    }

    private static NorskOrganisasjon norskOrganisasjon(EgenNæring norskOrg) {
        return new NorskOrganisasjon()
                .withVedlegg(egenNæringVedleggFraIDs(norskOrg.vedlegg()))
                .withBeskrivelseAvEndring(norskOrg.beskrivelseEndring())
                .withNaerRelasjon(norskOrg.nærRelasjon())
                .withEndringsDato(norskOrg.endringsDato())
                .withOppstartsdato(norskOrg.oppstartsDato())
                .withErNyoppstartet(norskOrg.erNyOpprettet())
                .withErNyIArbeidslivet(norskOrg.erNyIArbeidslivet())
                .withErVarigEndring(norskOrg.erVarigEndring())
                .withNaeringsinntektBrutto(BigInteger.valueOf(norskOrg.næringsinntektBrutto()))
                .withNavn(norskOrg.orgName())
                .withOrganisasjonsnummer(norskOrg.orgNummer().value())
                .withPeriode(periodeFra(norskOrg.periode()))
                .withRegnskapsfoerer(regnskapsFørerFra(norskOrg.regnskapsførere()))
                .withVirksomhetstype(virksomhetsTyperFra(norskOrg.virksomhetsTyper()))
                .withOppstartsdato(norskOrg.oppstartsDato());
    }

    private static List<JAXBElement<Object>> egenNæringVedleggFraIDs(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(s -> FP_FACTORY_V3.createEgenNaeringVedlegg(new Vedlegg().withId(s)))
                .toList();
    }

    private static List<AnnenOpptjening> andreOpptjeningerFra(
            List<no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening> annenOpptjening) {
        return safeStream(annenOpptjening)
                .map(V3DomainMapperCommon::annenOpptjeningFra)
                .toList();
    }

    private static AnnenOpptjening annenOpptjeningFra(
            no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening annen) {
        return Optional.ofNullable(annen)
                .map(V3DomainMapperCommon::create)
                .orElse(null);
    }

    private static AnnenOpptjeningTyper create(String kode) {
        var type = new AnnenOpptjeningTyper().withKode(kode);
        type.setKodeverk(type.getKodeverk());
        return type;
    }

    private static AnnenOpptjening create(no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening annen) {
        return new AnnenOpptjening()
                .withVedlegg(annenOpptjeningVedleggFra(annen.vedlegg()))
                .withType(annenOpptjeningTypeFra(annen.type()))
                .withPeriode(periodeFra(annen.periode()));
    }

    private static AnnenOpptjeningTyper annenOpptjeningTypeFra(AnnenOpptjeningType type) {
        return Optional.ofNullable(type)
                .map(AnnenOpptjeningType::name)
                .map(V3DomainMapperCommon::create)
                .orElse(null);
    }

    private static Regnskapsfoerer regnskapsFørerFra(List<Regnskapsfører> regnskapsførere) {
        if (regnskapsførere == null || regnskapsførere.isEmpty()) {
            return null;
        }
        var regnskapsfører = regnskapsførere.get(0);
        return new Regnskapsfoerer()
                .withTelefon(regnskapsfører.telefon())
                .withNavn(regnskapsfører.navn());
    }

    static Land landFra(CountryCode land) {
        if (XK.equals(land)) {
            return KOSOVO; // https://jira.adeo.no/browse/PFP-6077
        }
        return Optional.ofNullable(land)
                .map(s -> landFra(s.getAlpha3()))
                .orElse(null);
    }

    private static List<UtenlandskArbeidsforhold> utenlandskeArbeidsforholdFra(
            List<no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskArbeidsforhold> arbeidsforhold) {
        return safeStream(arbeidsforhold)
                .map(V3DomainMapperCommon::utenlandskArbeidsforholdFra)
                .toList();
    }

    private static UtenlandskArbeidsforhold utenlandskArbeidsforholdFra(
            no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskArbeidsforhold forhold) {
        return new UtenlandskArbeidsforhold()
                .withVedlegg(utenlandsArbeidsforholdVedleggFra(forhold.vedlegg()))
                .withArbeidsgiversnavn(forhold.arbeidsgiverNavn())
                .withArbeidsland(landFra(forhold.land()))
                .withPeriode(periodeFra(forhold.periode()));
    }

    private static List<JAXBElement<Object>> utenlandsArbeidsforholdVedleggFra(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(s -> FP_FACTORY_V3.createUtenlandskArbeidsforholdVedlegg(new Vedlegg().withId(s)))
                .toList();
    }

    static Bruker søkerFra(AktørId aktørId, Søker søker) {
        return new Bruker()
                .withAktoerId(aktørId.value())
                .withSoeknadsrolle(brukerRolleFra(søker.søknadsRolle()));
    }

    static List<Vedlegg> vedleggFra(
            List<? extends no.nav.foreldrepenger.common.domain.felles.Vedlegg> vedlegg) {
        return safeStream(vedlegg)
                .map(V3DomainMapperCommon::vedleggFra)
                .toList();
    }

    private static Innsendingstype innsendingstypeFra(InnsendingsType innsendingsType) {

        return switch (innsendingsType) {
            case SEND_SENERE -> innsendingsTypeMedKodeverk(SEND_SENERE);
            case LASTET_OPP -> innsendingsTypeMedKodeverk(LASTET_OPP);
            default -> throw new UnexpectedInputException("Innsendingstype " + innsendingsType + "  ikke støttet");
        };
    }

    private static Frilans frilansFra(no.nav.foreldrepenger.common.domain.felles.opptjening.Frilans frilans) {
        return Optional.ofNullable(frilans)
                .map(V3DomainMapperCommon::create)
                .orElse(null);
    }

    private static Frilans create(no.nav.foreldrepenger.common.domain.felles.opptjening.Frilans frilans) {
        var frilansOppdragsList = frilans.frilansOppdrag();
        return new Frilans()
                .withErNyoppstartet(frilans.nyOppstartet())
                .withHarInntektFraFosterhjem(frilans.harInntektFraFosterhjem())
                .withNaerRelasjon(frilansOppdragsList != null && !frilansOppdragsList.isEmpty())
                .withPeriode(periodeFra(frilans.periode()))
                .withFrilansoppdrag(frilansOppdragFra(frilansOppdragsList));
    }

    private static List<Frilansoppdrag> frilansOppdragFra(List<FrilansOppdrag> frilansOppdrag) {
        return safeStream(frilansOppdrag)
                .map(V3DomainMapperCommon::frilansOppdragFra)
                .toList();
    }

    private static Frilansoppdrag frilansOppdragFra(FrilansOppdrag oppdrag) {
        return Optional.ofNullable(oppdrag)
                .map(V3DomainMapperCommon::create)
                .orElse(null);
    }

    private static Frilansoppdrag create(FrilansOppdrag oppdrag) {
        return new Frilansoppdrag()
                .withOppdragsgiver(oppdrag.oppdragsgiver())
                .withPeriode(periodeFra(oppdrag.periode()));
    }

    private static List<JAXBElement<Object>> annenOpptjeningVedleggFra(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(s -> FP_FACTORY_V3.createAnnenOpptjeningVedlegg(new Vedlegg().withId(s)))
                .toList();
    }

    private static Vedlegg vedleggFra(no.nav.foreldrepenger.common.domain.felles.Vedlegg vedlegg) {
        return new Vedlegg()
                .withId(vedlegg.getId())
                .withTilleggsinformasjon(vedlegg.getBeskrivelse())
                .withSkjemanummer(vedlegg.getDokumentType().name())
                .withInnsendingstype(innsendingstypeFra(vedlegg.getInnsendingsType()));
    }

    private static Spraakkode målformFra(Målform kode) {
        return målformFra(kode.name());
    }

    private static Spraakkode målformFra(String kode) {
        return new Spraakkode().withKode(kode);
    }

    private static Innsendingstype innsendingsTypeMedKodeverk(InnsendingsType type) {
        var typeMedKodeverk = new Innsendingstype().withKode(type.name());
        return typeMedKodeverk.withKodeverk(typeMedKodeverk.getKodeverk());
    }

    private static Brukerroller brukerRolleFra(BrukerRolle søknadsRolle) {
        return brukerRolleFra(søknadsRolle.name());
    }

    private static Brukerroller brukerRolleFra(String rolle) {
        var brukerRolle = new Brukerroller().withKode(rolle);
        return brukerRolle.withKodeverk(brukerRolle.getKodeverk());
    }

    private static Land landFra(String alpha3) {
        var land = new Land().withKode(alpha3);
        return land.withKodeverk(land.getKodeverk());
    }
}

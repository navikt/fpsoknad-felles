package no.nav.foreldrepenger.common.innsending.mappers;

import static com.neovisionaries.i18n.CountryCode.XK;
import static java.time.LocalDate.now;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.LASTET_OPP;
import static no.nav.foreldrepenger.common.domain.felles.InnsendingsType.SEND_SENERE;
import static no.nav.foreldrepenger.common.util.StreamUtil.safeStream;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neovisionaries.i18n.CountryCode;

import jakarta.xml.bind.JAXBElement;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.BrukerRolle;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.felles.InnsendingsType;
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjeningType;
import no.nav.foreldrepenger.common.domain.felles.opptjening.EgenNæring;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Regnskapsfører;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Virksomhetstype;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;
import no.nav.foreldrepenger.common.error.UnexpectedInputException;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Bruker;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Medlemskap;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.OppholdUtlandet;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Periode;
import no.nav.vedtak.felles.xml.soeknad.felles.v3.Vedlegg;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.AnnenOpptjening;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.EgenNaering;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Frilans;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.NorskOrganisasjon;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Opptjening;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.Regnskapsfoerer;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.UtenlandskArbeidsforhold;
import no.nav.vedtak.felles.xml.soeknad.foreldrepenger.v3.UtenlandskOrganisasjon;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.AnnenOpptjeningTyper;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Brukerroller;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Innsendingstype;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Land;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Spraakkode;
import no.nav.vedtak.felles.xml.soeknad.kodeverk.v3.Virksomhetstyper;

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
                .map(V3DomainMapperCommon::tilPeriode)
                .orElse(null);
    }

    private static Periode tilPeriode(ÅpenPeriode p) {
        return tilPeriode(p.fom(), p.tom());
    }

    static Opptjening opptjeningFra(no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening opptjening) {
        var opptjeningXml = new Opptjening();
        opptjeningXml.getUtenlandskArbeidsforhold().addAll(utenlandskeArbeidsforholdFra(opptjening.utenlandskArbeidsforhold()));
        opptjeningXml.setFrilans(frilansFra(opptjening.frilans()));
        opptjeningXml.getEgenNaering().addAll(egneNæringerFra(opptjening.egenNæring()));
        opptjeningXml.getAnnenOpptjening().addAll(andreOpptjeningerFra(opptjening.annenOpptjening()));
        return opptjeningXml;
    }

    static Medlemskap medlemsskapFra(Utenlandsopphold opphold, LocalDate relasjonsDato) {
        return Optional.ofNullable(opphold)
                .map(o -> create(o, relasjonsDato))
                .orElse(null);
    }

    private static Medlemskap create(Utenlandsopphold opphold, LocalDate relasjonsDato) {
        var medlemskap = new Medlemskap();
        medlemskap.getOppholdUtlandet().addAll(oppholdUtlandetFra(opphold));
        medlemskap.setINorgeVedFoedselstidspunkt(opphold.varINorge(relasjonsDato));
        return medlemskap;
    }

    private static List<OppholdUtlandet> oppholdUtlandetFra(Utenlandsopphold utenlandsopphold) {
        return safeStream(utenlandsopphold.opphold())
                .map(V3DomainMapperCommon::utenlandOppholdFra)
                .toList();
    }

    private static OppholdUtlandet utenlandOppholdFra(Utenlandsopphold.Opphold opphold) {
        return Optional.ofNullable(opphold)
                .map(V3DomainMapperCommon::tilUtenlandsOpphold)
                .orElse(null);
    }

    private static OppholdUtlandet tilUtenlandsOpphold(Utenlandsopphold.Opphold o) {
        var oppholdUtlandet = new OppholdUtlandet();
        oppholdUtlandet.setPeriode(tilPeriode(o.fom(), o.tom()));
        oppholdUtlandet.setLand(landFra(o.land()));
        return oppholdUtlandet;
    }

    private static Periode tilPeriode(LocalDate fom, LocalDate tom) {
        var periode = new Periode();
        periode.setFom(fom);
        periode.setTom(tom);
        return periode;
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
        var virksomhetstyper = new Virksomhetstyper();
        virksomhetstyper.setKode(type);
        virksomhetstyper.setKodeverk(virksomhetstyper.getKodeverk()); // bruker default fra getter..
        return virksomhetstyper;
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
        // Fiskere kan svare nei på om den er registert i norge og deretter velge norge for å unngå å fylle inn orgnummer
        // I dette tilfelle vil de bli lagret som utenlandsk næring
        if (CountryCode.NO.equals(egenNæring.registrertILand()) && egenNæring.orgNummer() != null) {
            return norskOrganisasjon(egenNæring);
        } else {
            return utenlandskOrganisasjon(egenNæring);
        }
    }

    private static UtenlandskOrganisasjon utenlandskOrganisasjon(EgenNæring utenlandskOrg) {
        var utenlandskOrganisasjon = new UtenlandskOrganisasjon();
        utenlandskOrganisasjon.getVedlegg().addAll(egenNæringVedleggFraIDs(utenlandskOrg.vedlegg()));
        utenlandskOrganisasjon.setBeskrivelseAvEndring(utenlandskOrg.beskrivelseEndring());
        utenlandskOrganisasjon.setNaerRelasjon(utenlandskOrg.nærRelasjon());
        utenlandskOrganisasjon.setEndringsDato(utenlandskOrg.endringsDato());
        utenlandskOrganisasjon.setOppstartsdato(utenlandskOrg.oppstartsDato());
        utenlandskOrganisasjon.setErNyoppstartet(utenlandskOrg.erNyOpprettet());
        utenlandskOrganisasjon.setErNyIArbeidslivet (utenlandskOrg.erNyIArbeidslivet());
        utenlandskOrganisasjon.setErVarigEndring(utenlandskOrg.erVarigEndring());
        utenlandskOrganisasjon.setNaeringsinntektBrutto(BigInteger.valueOf(utenlandskOrg.næringsinntektBrutto()));
        utenlandskOrganisasjon.setNavn(utenlandskOrg.orgName());
        utenlandskOrganisasjon.setRegistrertILand(landFra(utenlandskOrg.registrertILand()));
        utenlandskOrganisasjon.setPeriode(periodeFra(utenlandskOrg.periode()));
        utenlandskOrganisasjon.setRegnskapsfoerer(regnskapsFørerFra(utenlandskOrg.regnskapsførere()));
        utenlandskOrganisasjon.getVirksomhetstype().addAll(virksomhetsTyperFra(utenlandskOrg.virksomhetsTyper()));
        return utenlandskOrganisasjon;
    }

    private static NorskOrganisasjon norskOrganisasjon(EgenNæring norskOrg) {
        var norskOrganisasjon = new NorskOrganisasjon();
        norskOrganisasjon.getVedlegg().addAll(egenNæringVedleggFraIDs(norskOrg.vedlegg()));
        norskOrganisasjon.setBeskrivelseAvEndring(norskOrg.beskrivelseEndring());
        norskOrganisasjon.setNaerRelasjon(norskOrg.nærRelasjon());
        norskOrganisasjon.setEndringsDato(norskOrg.endringsDato());
        norskOrganisasjon.setOppstartsdato(norskOrg.oppstartsDato());
        norskOrganisasjon.setErNyoppstartet(norskOrg.erNyOpprettet());
        norskOrganisasjon.setErNyIArbeidslivet (norskOrg.erNyIArbeidslivet());
        norskOrganisasjon.setErVarigEndring(norskOrg.erVarigEndring());
        norskOrganisasjon.setNaeringsinntektBrutto(BigInteger.valueOf(norskOrg.næringsinntektBrutto()));
        norskOrganisasjon.setNavn(norskOrg.orgName());
        norskOrganisasjon.setOrganisasjonsnummer(norskOrg.orgNummer().value());
        norskOrganisasjon.setPeriode(periodeFra(norskOrg.periode()));
        norskOrganisasjon.setRegnskapsfoerer(regnskapsFørerFra(norskOrg.regnskapsførere()));
        norskOrganisasjon.getVirksomhetstype().addAll(virksomhetsTyperFra(norskOrg.virksomhetsTyper()));
        norskOrganisasjon.setOppstartsdato(norskOrg.oppstartsDato());
        return norskOrganisasjon;
    }

    private static List<JAXBElement<Object>> egenNæringVedleggFraIDs(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(referanse -> FP_FACTORY_V3.createEgenNaeringVedlegg(tilVedlegg(referanse)))
                .toList();
    }

    static Vedlegg tilVedlegg(String referanse) {
        Vedlegg vedlegg = new Vedlegg();
        vedlegg.setId(referanse);
        return vedlegg;
    }

    private static List<AnnenOpptjening> andreOpptjeningerFra(List<no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening> annenOpptjening) {
        return safeStream(annenOpptjening)
                .map(V3DomainMapperCommon::annenOpptjeningFra)
                .toList();
    }

    private static AnnenOpptjening annenOpptjeningFra(no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening annen) {
        return Optional.ofNullable(annen)
                .map(V3DomainMapperCommon::create)
                .orElse(null);
    }

    private static AnnenOpptjeningTyper create(String kode) {
        var type = new AnnenOpptjeningTyper();
        type.setKode(kode);
        type.setKodeverk(type.getKodeverk()); // bruker default fra getter..
        return type;
    }

    private static AnnenOpptjening create(no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening annen) {
        var annenOpptjening = new AnnenOpptjening();
        annenOpptjening.getVedlegg().addAll(annenOpptjeningVedleggFra(annen.vedlegg()));
        annenOpptjening.setType(annenOpptjeningTypeFra(annen.type()));
        annenOpptjening.setPeriode(periodeFra(annen.periode()));
        return annenOpptjening;
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
        var førsteRegnskapsfører = regnskapsførere.get(0);

        var regnskapsfoerer = new Regnskapsfoerer();
        regnskapsfoerer.setTelefon(førsteRegnskapsfører.telefon());
        regnskapsfoerer.setNavn(førsteRegnskapsfører.navn());
        return regnskapsfoerer;
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

    private static UtenlandskArbeidsforhold utenlandskArbeidsforholdFra(no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskArbeidsforhold forhold) {
        var utenlandskArbeidsforhold = new UtenlandskArbeidsforhold();
        utenlandskArbeidsforhold.getVedlegg().addAll(utenlandsArbeidsforholdVedleggFra(forhold.vedlegg()));
        utenlandskArbeidsforhold.setArbeidsgiversnavn(forhold.arbeidsgiverNavn());
        utenlandskArbeidsforhold.setArbeidsland(landFra(forhold.land()));
        utenlandskArbeidsforhold.setPeriode(periodeFra(forhold.periode()));
        return utenlandskArbeidsforhold;
    }

    private static List<JAXBElement<Object>> utenlandsArbeidsforholdVedleggFra(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(referanse -> FP_FACTORY_V3.createUtenlandskArbeidsforholdVedlegg(tilVedlegg(referanse)))
                .toList();
    }

    static Bruker søkerFra(AktørId aktørId, Søker søker) {
        var bruker = new Bruker();
        bruker.setAktoerId(aktørId.value());
        bruker.setSoeknadsrolle(brukerRolleFra(søker.søknadsRolle()));
        return bruker;
    }

    static List<Vedlegg> vedleggFra(List<? extends no.nav.foreldrepenger.common.domain.felles.Vedlegg> vedlegg) {
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
        var frilansXML = new Frilans();
        frilansXML.getPeriode().add(periodeFra(frilans.periode()));
        // TODO: disse under + frilansXML.getFrilansoppdrag() kan fjernes fra kontrakt
        frilansXML.setHarInntektFraFosterhjem(false);
        frilansXML.setNaerRelasjon(false);
        var erNyoppstartet = frilans.periode().fom().isAfter(now().minusMonths(3));
        frilansXML.setErNyoppstartet(erNyoppstartet);
        return frilansXML;
    }

    private static List<JAXBElement<Object>> annenOpptjeningVedleggFra(List<VedleggReferanse> vedlegg) {
        return safeStream(vedlegg)
                .filter(Objects::nonNull)
                .map(VedleggReferanse::referanse)
                .map(referanse -> FP_FACTORY_V3.createAnnenOpptjeningVedlegg(tilVedlegg(referanse)))
                .toList();
    }

    private static Vedlegg vedleggFra(no.nav.foreldrepenger.common.domain.felles.Vedlegg vedlegg) {
        var vedleggXML = new Vedlegg();
        vedleggXML.setId(vedlegg.getId());
        vedleggXML.setTilleggsinformasjon(vedlegg.getBeskrivelse());
        vedleggXML.setSkjemanummer(vedlegg.getDokumentType().name());
        vedleggXML.setInnsendingstype(innsendingstypeFra(vedlegg.getInnsendingsType()));
        return vedleggXML;
    }

    private static Spraakkode målformFra(Målform kode) {
        return målformFra(kode.name());
    }

    private static Spraakkode målformFra(String kode) {
        var spraakkkode = new Spraakkode();
        spraakkkode.setKode(kode);
        return spraakkkode;
    }

    private static Innsendingstype innsendingsTypeMedKodeverk(InnsendingsType type) {
        var innsendingstype = new Innsendingstype();
        innsendingstype.setKode(type.name());
        innsendingstype.setKodeverk(innsendingstype.getKodeverk()); // TODO: Fjern..
        return innsendingstype;
    }

    private static Brukerroller brukerRolleFra(BrukerRolle søknadsRolle) {
        return brukerRolleFra(søknadsRolle.name());
    }

    private static Brukerroller brukerRolleFra(String rolle) {
        var brukerRolle = new Brukerroller();
        brukerRolle.setKode(rolle);
        brukerRolle.setKodeverk(brukerRolle.getKodeverk()); // TODO: Fjern..
        return brukerRolle;
    }

    private static Land landFra(String alpha3) {
        var land = new Land();
        land.setKode(alpha3);
        land.setKodeverk(land.getKodeverk()); // TODO: Fjern..
        return land;
    }
}

package no.nav.foreldrepenger.common.util;

import static com.google.common.collect.Lists.newArrayList;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Collections.singletonList;
import static no.nav.foreldrepenger.common.domain.Orgnummer.MAGIC_ORG;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000062;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000063;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I500002;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I500005;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.medlemsskap;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.søker;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.valgfrittVedlegg;
import static no.nav.foreldrepenger.common.domain.felles.opptjening.Virksomhetstype.FISKE;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType.FEDREKVOTE;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.bytesFra;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.DokumentType;
import no.nav.foreldrepenger.common.domain.felles.Ettersending;
import no.nav.foreldrepenger.common.domain.felles.EttersendingsType;
import no.nav.foreldrepenger.common.domain.felles.InnsendingsType;
import no.nav.foreldrepenger.common.domain.felles.ProsentAndel;
import no.nav.foreldrepenger.common.domain.felles.TestUtils;
import no.nav.foreldrepenger.common.domain.felles.ValgfrittVedlegg;
import no.nav.foreldrepenger.common.domain.felles.Vedlegg;
import no.nav.foreldrepenger.common.domain.felles.VedleggMetaData;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UtenlandskForelder;
import no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening;
import no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjeningType;
import no.nav.foreldrepenger.common.domain.felles.opptjening.EgenNæring;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Frilans;
import no.nav.foreldrepenger.common.domain.felles.opptjening.FrilansOppdrag;
import no.nav.foreldrepenger.common.domain.felles.opptjening.NorskOrganisasjon;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Regnskapsfører;
import no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskArbeidsforhold;
import no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskOrganisasjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Omsorgsovertakelse;
import no.nav.foreldrepenger.common.domain.felles.ÅpenPeriode;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Dekningsgrad;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Endringssøknad;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Foreldrepenger;
import no.nav.foreldrepenger.common.domain.foreldrepenger.Rettigheter;
import no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.Fordeling;
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
import no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.DelvisTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.HelTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.IngenTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Frilanser;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.PrivatArbeidsgiver;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Virksomhet;

public class ForeldrepengerTestUtils {

    public static final Fødselsnummer NORSK_FORELDER_FNR = new Fødselsnummer("11111111111");
    public static final String ID142 = "V142";
    public static final String ID143 = "V143";
    public static final String ID144 = "V144";

    public static final List<Vedlegg> TO_VEDLEGG = newArrayList(
            valgfrittVedlegg(ID142, InnsendingsType.LASTET_OPP),
            valgfrittVedlegg(ID143, InnsendingsType.LASTET_OPP));
    public static final ValgfrittVedlegg VEDLEGG1 = opplastetVedlegg(ID142, I500002);
    public static final ValgfrittVedlegg VEDLEGG2 = opplastetVedlegg(ID143, I500005);
    public static final ValgfrittVedlegg VEDLEGG3 = opplastetVedlegg(ID144, I000062);

    private static final ValgfrittVedlegg IKKE_OPPLASTETV1 = ikkeOpplastet(ID142, I000063);
    private static final ValgfrittVedlegg IKKE_OPPLASTETV2 = ikkeOpplastet(ID143, I000063);

    public static Søknad foreldrepengeSøknad() {
        return new Søknad(LocalDate.now(), TestUtils.søker(), foreldrepenger(false), VEDLEGG1);
    }

    public static Søknad foreldrepengeSøknadUtenVedlegg() {
        return new Søknad(LocalDate.now(), TestUtils.søker(), foreldrepenger(false));
    }

    public static Søknad foreldrepengesøknadMedEttVedlegg() {
        return foreldrepengesøknad(false, VEDLEGG1);
    }

    public static Søknad foreldrepengesøknadMedEttOpplastetEttIkkeOpplastetVedlegg() {
        return foreldrepengesøknad(false, VEDLEGG1, IKKE_OPPLASTETV2);
    }

    public static Søknad foreldrepengesøknadMedToVedlegg() {
        return foreldrepengesøknad(false, VEDLEGG1, VEDLEGG2);
    }

    public static Søknad foreldrepengesøknadMedEttIkkeOpplastedVedlegg(boolean utland) {
        return foreldrepengesøknad(utland, IKKE_OPPLASTETV1);
    }

    public static Søknad foreldrepengesøknad(boolean utland, Vedlegg... vedlegg) {
        return søknad(foreldrepenger(utland, vedleggRefs(vedlegg)), vedlegg);
    }


    public static Søknad svp() {
        return svp(true);
    }

    public static Søknad svp(boolean vedlegg) {
        if (vedlegg) {
            return søknad(svangerskapspenger(vedleggRefs(VEDLEGG1)), VEDLEGG1);
        }
        return søknad(svangerskapspenger());

    }

    public static Svangerskapspenger svangerskapspenger(String... vedleggRefs) {
        return Svangerskapspenger.builder()
                .termindato(LocalDate.now().plusMonths(1))
                .medlemsskap(medlemsskap())
                .opptjening(opptjening())
                .tilrettelegging(tilrettelegging(vedleggRefs))
                .build();
    }

    public static Endringssøknad endringssøknad(Vedlegg... vedlegg) {
        return new Endringssøknad(LocalDate.now(), søker(),
                fordeling(vedleggRefs(vedlegg)), norskForelder(),
                fødsel(),
                rettigheter(),
                "42V3", vedlegg);
    }

    public static Søknad søknad(Ytelse ytelse, Vedlegg... vedlegg) {
        var søknad = new Søknad(LocalDate.now(), TestUtils.søker(), ytelse, List.of(vedlegg));
        søknad.setTilleggsopplysninger("Opplysninger av den kjente tilleggtypen");
        søknad.setBegrunnelseForSenSøknad("Begrunner det ikke nie");
        return søknad;
    }

    private static String[] vedleggRefs(Vedlegg... vedlegg) {
        return Arrays.stream(vedlegg)
                .map(Vedlegg::getId)
                .toArray(String[]::new);
    }

    public static Ettersending ettersending() {
        return new Ettersending("42", EttersendingsType.FORELDREPENGER, TO_VEDLEGG, null);
    }

    private static List<Tilrettelegging> tilrettelegging(String... vedleggRefs) {
        return Lists.newArrayList(helTilrettelegging(vedleggRefs), helTilrettelegging(vedleggRefs),
                delvisTilrettelegging(vedleggRefs),
                ingenTilrettelegging(vedleggRefs));
    }

    private static Tilrettelegging ingenTilrettelegging(String... vedleggRefs) {
        return new IngenTilrettelegging(frilanser(), LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(2),
                List.of(vedleggRefs));

    }

    public static Tilrettelegging delvisTilrettelegging(String... vedleggRefs) {
        return new DelvisTilrettelegging(privat(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2),
                new ProsentAndel(77d), List.of(vedleggRefs));
    }

    private static Tilrettelegging helTilrettelegging(String... vedleggRefs) {
        return new HelTilrettelegging(virksomhet(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), List.of(vedleggRefs));
    }

    private static Arbeidsforhold virksomhet() {
        return new Virksomhet(MAGIC_ORG);
    }

    private static Arbeidsforhold privat() {
        return new PrivatArbeidsgiver(NORSK_FORELDER_FNR);
    }

    private static Arbeidsforhold frilanser() {
        return new Frilanser("risiko", "tiltak");
    }

    public static Foreldrepenger foreldrepenger(boolean utland, String... vedleggRefs) {
        return Foreldrepenger.builder()
                .rettigheter(rettigheter())
                .annenForelder(norskForelder())
                .dekningsgrad(Dekningsgrad.HUNDRE)
                .fordeling(fordeling(vedleggRefs))
                .opptjening(opptjening(vedleggRefs))
                .relasjonTilBarn(termin())
                .medlemsskap(medlemsskap(utland))
                .build();
    }

    public static Opptjening opptjening(String... vedleggRefs) {
        return new Opptjening(Collections.singletonList(utenlandskArbeidsforhold(vedleggRefs)),
                egneNæringer(vedleggRefs),
                andreOpptjeninger(vedleggRefs), frilans());
    }

    private static Frilans frilans() {
        return new Frilans(åpenPeriode(true), true, true, true,
                List.of(new FrilansOppdrag("fattern", åpenPeriode(true)),
                        new FrilansOppdrag(
                                "den andre bror min og samtidig en fryktelig lang tekst som straks må bryte over til ny linje",
                                åpenPeriode(true)),
                        new FrilansOppdrag("far min", åpenPeriode(true))
                ));
    }

    private static List<AnnenOpptjening> andreOpptjeninger(String... vedleggRefs) {
        return List.of(annenOpptjening(vedleggRefs));
    }

    private static List<EgenNæring> egneNæringer(String... vedleggRefs) {
        return List.of(utenlandskEgenNæring(vedleggRefs), norskEgenNæring(vedleggRefs));
    }

    public static UtenlandskForelder utenlandskForelder() {
        return new UtenlandskForelder("42", CountryCode.SE, "Pedro Bandolero");
    }

    public static NorskForelder norskForelder() {
        return new NorskForelder(NORSK_FORELDER_FNR, "Åge Mañana Pålsen");
    }

    public static Adopsjon adopsjon() {
        return new Adopsjon(0, LocalDate.now(), true, false, null, LocalDate.now(),
                List.of(LocalDate.now()));
    }

    public static ÅpenPeriode åpenPeriode() {
        return åpenPeriode(false);
    }

    public static ÅpenPeriode åpenPeriode(boolean end) {
        return end ? new ÅpenPeriode(LocalDate.now().minusMonths(5), LocalDate.now())
                : new ÅpenPeriode(LocalDate.now().minusMonths(5));
    }

    public static Omsorgsovertakelse omsorgsovertakelse() {
        return new Omsorgsovertakelse(LocalDate.now(), LocalDate.now());
    }

    public static UtenlandskOrganisasjon utenlandskEgenNæring(String... vedleggRefs) {
        return UtenlandskOrganisasjon.builder()
                .vedlegg(List.of(vedleggRefs))
                .registrertILand(CountryCode.UG)
                .periode(åpenPeriode())
                .regnskapsførere(List.of(new Regnskapsfører("Rein Åge Skapsfører", "+4799999999")))
                .erNyOpprettet(true)
                .erVarigEndring(true)
                .erNyIArbeidslivet(false)
                .næringsinntektBrutto(100_000)
                .orgName("Utenlandsk org")
                .virksomhetsTyper(List.of(FISKE))
                .beskrivelseEndring(
                        "Endringer skjer fort i verdens største land (utlandet) og ikke minst skjer det mye med linjebryting")
                .nærRelasjon(true)
                .endringsDato(LocalDate.now()).build();
    }

    public static NorskOrganisasjon norskEgenNæring(String... vedleggRefs) {
        return NorskOrganisasjon.builder()
                .vedlegg(List.of(vedleggRefs))
                .periode(åpenPeriode())
                .regnskapsførere(Collections.singletonList(new Regnskapsfører("Rein Åge Kapsfører", "+4799999999")))
                .erNyOpprettet(true)
                .erVarigEndring(true)
                .erNyIArbeidslivet(true)
                .erNyOpprettet(true)
                .næringsinntektBrutto(100_000)
                .orgName("Norsk org")
                .orgNummer(MAGIC_ORG)
                .virksomhetsTyper(Collections.singletonList(FISKE))
                .beskrivelseEndring("Ting endrer seg i Norge også")
                .nærRelasjon(true)
                .endringsDato(LocalDate.now()).build();
    }

    public static AnnenOpptjening annenOpptjening(String... vedleggRefs) {
        return new AnnenOpptjening(AnnenOpptjeningType.VENTELØNN_VARTPENGER, åpenPeriode(), List.of(vedleggRefs));
    }

    public static UtenlandskArbeidsforhold utenlandskArbeidsforhold(String... vedleggRefs) {
        return UtenlandskArbeidsforhold.builder()
                .vedlegg(List.of(vedleggRefs))
                .arbeidsgiverNavn("Brzezánski")
                .land(CountryCode.PL)
                .periode(åpenPeriode()).build();
    }

    public static List<LukketPeriodeMedVedlegg> perioder(String... vedleggRefs) {
        return newArrayList(
                oppholdsPeriode(vedleggRefs),
                overføringsPeriode(vedleggRefs),
                utsettelsesPeriode(vedleggRefs),
                uttaksPeriode(vedleggRefs),
                gradertPeriode(vedleggRefs));
    }

    public static FremtidigFødsel termin() {
        return new FremtidigFødsel(LocalDate.now(), LocalDate.now());
    }

    public static Fødsel fødsel() {
        return Fødsel.builder()
                .antallBarn(1)
                .fødselsdato(singletonList(LocalDate.now().minusMonths(2)))
                .build();
    }

    public static UttaksPeriode uttaksPeriode(LocalDate fom, LocalDate tom) {
        return UttaksPeriode.UttaksPeriodeBuilder()
                .fom(fom)
                .tom(tom)
                .uttaksperiodeType(FEDREKVOTE)
                .ønskerSamtidigUttak(true)
                .morsAktivitetsType(MorsAktivitet.ARBEID)
                .ønskerFlerbarnsdager(true)
                .samtidigUttakProsent(new ProsentAndel(100.0d))
                .vedlegg(List.of())
                .build();
    }

    public static UttaksPeriode uttaksPeriode(String... vedleggRefs) {
        return UttaksPeriode.UttaksPeriodeBuilder()
                .fom(ukeDagNær(LocalDate.now().plusMonths(3)))
                .tom(ukeDagNær(LocalDate.now().plusMonths(4)))
                .uttaksperiodeType(FEDREKVOTE)
                .ønskerSamtidigUttak(true)
                .morsAktivitetsType(MorsAktivitet.ARBEID_OG_UTDANNING)
                .ønskerFlerbarnsdager(true)
                .samtidigUttakProsent(new ProsentAndel(42d))
                .vedlegg(List.of(vedleggRefs))
                .build();
    }

    public static UttaksPeriode gradertPeriode(String... vedleggRefs) {
        return GradertUttaksPeriode.GradertUttaksPeriodeBuilder()
                .fom(ukeDagNær(LocalDate.now().plusMonths(4)))
                .tom(LocalDate.now().plusMonths(5))
                .uttaksperiodeType(FEDREKVOTE)
                .ønskerSamtidigUttak(true)
                .morsAktivitetsType(MorsAktivitet.ARBEID_OG_UTDANNING)
                .ønskerFlerbarnsdager(true)
                .arbeidstidProsent(new ProsentAndel(75d))
                .erArbeidstaker(true)
                .arbeidsForholdSomskalGraderes(true)
                .virksomhetsnummer(Collections.singletonList("22222222222"))
                .frilans(true)
                .selvstendig(true)
                .vedlegg(List.of(vedleggRefs))
                .build();
    }

    public static OverføringsPeriode overføringsPeriode(String... vedleggRefs) {
        return new OverføringsPeriode(ukeDagNær(LocalDate.now()), ukeDagNær(LocalDate.now().plusMonths(1)),
                Overføringsårsak.ALENEOMSORG, StønadskontoType.FEDREKVOTE, List.of(vedleggRefs));
    }

    public static OppholdsPeriode oppholdsPeriode(String... vedleggRefs) {
        return new OppholdsPeriode(ukeDagNær(LocalDate.now().plusMonths(1)), ukeDagNær(LocalDate.now().plusMonths(2)),
                Oppholdsårsak.UTTAK_FEDREKVOTE_ANNEN_FORELDER, List.of(vedleggRefs));
    }

    public static UtsettelsesPeriode utsettelsesPeriode(String... vedleggRefs) {
        return new UtsettelsesPeriode(ukeDagNær(LocalDate.now().plusMonths(2)),
                ukeDagNær(LocalDate.now().plusMonths(3)), true, Collections.singletonList("222"),
                UtsettelsesÅrsak.INSTITUSJONSOPPHOLD_BARNET, null, List.of(vedleggRefs));
    }

    public static Fordeling fordeling(String... vedleggRefs) {
        return new Fordeling(true, perioder(vedleggRefs));
    }

    public static Rettigheter rettigheter() {
        return new Rettigheter(true, true, true, false);
    }

    private static ValgfrittVedlegg opplastetVedlegg(String id, DokumentType type) {
        try {
            var vedleggMetaData = new VedleggMetaData(id, InnsendingsType.LASTET_OPP, type);
            return new ValgfrittVedlegg(vedleggMetaData,
                bytesFra("pdf/terminbekreftelse.pdf"));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private static ValgfrittVedlegg ikkeOpplastet(String id, DokumentType type) {
        try {
            var vedleggMetaData = new VedleggMetaData(id, InnsendingsType.SEND_SENERE, type);
            return new ValgfrittVedlegg(vedleggMetaData, null);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static LocalDate ukeDagNær(LocalDate dato) {
        LocalDate d = dato;
        while (!erUkedag(d)) {
            d = d.plusDays(1);
        }
        return d;
    }

    private static boolean erUkedag(LocalDate dato) {
        return !dato.getDayOfWeek().equals(SATURDAY) && !dato.getDayOfWeek().equals(SUNDAY);
    }
}

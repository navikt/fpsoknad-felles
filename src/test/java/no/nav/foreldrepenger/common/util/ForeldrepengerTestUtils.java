package no.nav.foreldrepenger.common.util;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Collections.singletonList;
import static no.nav.foreldrepenger.common.domain.Orgnummer.MAGIC_ORG;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000062;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000063;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000065;
import static no.nav.foreldrepenger.common.domain.felles.DokumentType.I000108;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.opphold;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.søker;
import static no.nav.foreldrepenger.common.domain.felles.TestUtils.valgfrittVedlegg;
import static no.nav.foreldrepenger.common.domain.felles.opptjening.Virksomhetstype.FISKE;
import static no.nav.foreldrepenger.common.domain.foreldrepenger.fordeling.StønadskontoType.FEDREKVOTE;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.bytesFra;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Saksnummer;
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
import no.nav.foreldrepenger.common.domain.felles.VedleggReferanse;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UtenlandskForelder;
import no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjening;
import no.nav.foreldrepenger.common.domain.felles.opptjening.AnnenOpptjeningType;
import no.nav.foreldrepenger.common.domain.felles.opptjening.EgenNæring;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Frilans;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Regnskapsfører;
import no.nav.foreldrepenger.common.domain.felles.opptjening.UtenlandskArbeidsforhold;
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
import no.nav.foreldrepenger.common.domain.svangerskapspenger.AvtaltFerie;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.Svangerskapspenger;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.DelvisTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.HelTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.IngenTilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Frilanser;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.PrivatArbeidsgiver;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Virksomhet;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilretteleggingsbehov.Tilretteleggingbehov;

public class ForeldrepengerTestUtils {

    public static final Fødselsnummer NORSK_FORELDER_FNR = new Fødselsnummer("11111111111");
    public static final String ID142 = "V142";
    public static final String ID143 = "V143";
    public static final String ID144 = "V144";

    public static final List<Vedlegg> TO_VEDLEGG = Stream.of(
            valgfrittVedlegg(ID142, InnsendingsType.LASTET_OPP),
            valgfrittVedlegg(ID143, InnsendingsType.LASTET_OPP))
            .collect(Collectors.toList());
    public static final ValgfrittVedlegg VEDLEGG1 = opplastetVedlegg(ID142, I000065);
    public static final ValgfrittVedlegg VEDLEGG2 = opplastetVedlegg(ID143, I000108);
    public static final ValgfrittVedlegg VEDLEGG3 = opplastetVedlegg(ID144, I000062);

    private static final ValgfrittVedlegg IKKE_OPPLASTETV1 = ikkeOpplastet(ID142, I000063);
    private static final ValgfrittVedlegg IKKE_OPPLASTETV2 = ikkeOpplastet(ID143, I000063);

    public static Søknad foreldrepengesøknad() {
        return new Søknad(LocalDate.now(), TestUtils.søker(), foreldrepenger(false), null, List.of(VEDLEGG1));
    }

    public static Søknad foreldrepengesøknad(boolean utland, Vedlegg... vedlegg) {
        return søknad(foreldrepenger(utland, vedleggRefs(vedlegg)), vedlegg);
    }

    public static Søknad foreldrepengesøknadUtenVedlegg() {
        return new Søknad(LocalDate.now(), TestUtils.søker(), foreldrepenger(false), null, null);
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

    public static Søknad svp() {
        return svp(true);
    }

    public static Søknad svp(boolean vedlegg) {
        if (vedlegg) {
            return søknad(svangerskapspenger(vedleggRefs(VEDLEGG1)), VEDLEGG1);
        }
        return søknad(svangerskapspenger());

    }

    public static Svangerskapspenger svangerskapspenger(VedleggReferanse... vedleggRefs) {
        var ferie = new AvtaltFerie(virksomhet(), LocalDate.now().plusDays(10), LocalDate.now().plusDays(20));
        return new Svangerskapspenger(
                LocalDate.now().plusMonths(1),
                null,
                opphold(),
                opptjening(),
                tilrettelegging(vedleggRefs),
                null,
                List.of(ferie)
        );
    }

    public static Endringssøknad endringssøknad(Vedlegg... vedlegg) {
        return new Endringssøknad(
                LocalDate.now(),
                søker(),
                new Foreldrepenger(
                        norskForelder(),
                        fødsel(),
                        rettigheter(),
                        null,
                        null,
                        fordeling(vedleggRefs(vedlegg)),
                        null
                ),
                null,
                List.of(vedlegg),
                new Saksnummer("123456789")

        );
    }

    public static Søknad søknad(Ytelse ytelse, Vedlegg... vedlegg) {
        return new Søknad(LocalDate.now(), TestUtils.søker(), ytelse, "Opplysninger av den kjente tilleggtypen", List.of(vedlegg));
    }

    public static VedleggReferanse[] vedleggRefs(Vedlegg... vedlegg) {
        return Arrays.stream(vedlegg)
                .map(Vedlegg::getMetadata)
                .map(VedleggMetaData::id)
                .toArray(VedleggReferanse[]::new);
    }

    public static Ettersending ettersending() {
        return new Ettersending(new Saksnummer("42"), EttersendingsType.FORELDREPENGER, TO_VEDLEGG, null);
    }

    private static List<Tilrettelegging> tilrettelegging(VedleggReferanse... vedleggRefs) {
        return Stream.of(helTilrettelegging(vedleggRefs),
                        helTilrettelegging(vedleggRefs),
                        delvisTilrettelegging(vedleggRefs),
                        ingenTilrettelegging(vedleggRefs))
                .collect(Collectors.toList());
    }

    private static Tilrettelegging ingenTilrettelegging(VedleggReferanse... vedleggRefs) {
        return new IngenTilrettelegging(frilanser(), LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(2),
                List.of(vedleggRefs));
    }

    public static Tilretteleggingbehov.Tilrettelegging helTilrettelegging() {
        return new Tilretteleggingbehov.HelTilrettelegging(LocalDate.now().plusMonths(1));
    }

    public static Tilretteleggingbehov.Tilrettelegging delTilrettelegging() {
        return new Tilretteleggingbehov.DelvisTilrettelegging(LocalDate.now().plusMonths(2), 77.0);
    }

    public static Tilretteleggingbehov.Tilrettelegging ingenTilrettelegging() {
        return new Tilretteleggingbehov.IngenTilrettelegging(LocalDate.now().plusMonths(2));
    }

    public static Tilrettelegging delvisTilrettelegging(VedleggReferanse... vedleggRefs) {
        return new DelvisTilrettelegging(privat(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2),
                new ProsentAndel(77d), List.of(vedleggRefs));
    }

    private static Tilrettelegging helTilrettelegging(VedleggReferanse... vedleggRefs) {
        return new HelTilrettelegging(virksomhet(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(2), List.of(vedleggRefs));
    }

    public static Arbeidsforhold virksomhet() {
        return new Virksomhet(MAGIC_ORG);
    }

    private static Arbeidsforhold privat() {
        return new PrivatArbeidsgiver(NORSK_FORELDER_FNR);
    }

    private static Arbeidsforhold frilanser() {
        return new Frilanser("risiko", "tiltak");
    }

    public static Foreldrepenger foreldrepenger(boolean utland, VedleggReferanse... vedleggRefs) {
        return new Foreldrepenger(
                norskForelder(),
                termin(),
                rettigheter(),
                Dekningsgrad.HUNDRE,
                opptjening(vedleggRefs),
                fordeling(vedleggRefs),
                opphold(utland)
        );
    }

    public static Opptjening opptjening(VedleggReferanse... vedleggRefs) {
        return new Opptjening(Collections.singletonList(utenlandskArbeidsforhold(vedleggRefs)),
                egneNæringer(vedleggRefs),
                andreOpptjeninger(vedleggRefs), frilans());
    }

    private static Frilans frilans() {
        return new Frilans(åpenPeriode(true), true);
    }

    private static List<AnnenOpptjening> andreOpptjeninger(VedleggReferanse... vedleggRefs) {
        return List.of(annenOpptjening(vedleggRefs));
    }

    private static List<EgenNæring> egneNæringer(VedleggReferanse... vedleggRefs) {
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

    public static EgenNæring utenlandskEgenNæring(VedleggReferanse... vedleggRefs) {
        return new EgenNæring(
                CountryCode.UG,
                null,
                "Utenlandsk org",
                List.of(FISKE),
                åpenPeriode(),
                true,
                List.of(new Regnskapsfører("Rein Åge Skapsfører", "+4799999999")),
                true,
                true,
                false,
                100_000,
                LocalDate.now(),
                null,
                "Endringer skjer fort i verdens største land (utlandet) og ikke minst skjer det mye med linjebryting",
                new ProsentAndel(100.0),
                List.of(vedleggRefs)
        );
    }

    public static EgenNæring norskEgenNæring(VedleggReferanse... vedleggRefs) {
        return new EgenNæring(
                CountryCode.NO,
                MAGIC_ORG,
                "Norsk org",
                singletonList(FISKE),
                åpenPeriode(),
                true,
                singletonList(new Regnskapsfører("Rein Åge Kapsfører", "+4799999999")),
                true,
                true,
                true,
                100_000,
                LocalDate.now(),
                null,
                "Ting endrer seg i Norge også",
                new ProsentAndel(100.0),
                List.of(vedleggRefs)
        );
    }

    public static AnnenOpptjening annenOpptjening(VedleggReferanse... vedleggRefs) {
        return new AnnenOpptjening(AnnenOpptjeningType.VENTELØNN_VARTPENGER, åpenPeriode(), List.of(vedleggRefs));
    }

    public static UtenlandskArbeidsforhold utenlandskArbeidsforhold(VedleggReferanse... vedleggRefs) {
        return new UtenlandskArbeidsforhold(
                "Brzezánski",
                åpenPeriode(),
                List.of(vedleggRefs),
                CountryCode.PL
        );
    }

    public static List<LukketPeriodeMedVedlegg> perioder(VedleggReferanse... vedleggRefs) {
        return Stream.of(
                oppholdsPeriode(vedleggRefs),
                overføringsPeriode(vedleggRefs),
                utsettelsesPeriode(vedleggRefs),
                uttaksPeriode(vedleggRefs),
                gradertPeriode(vedleggRefs))
                .collect(Collectors.toList());
    }

    public static FremtidigFødsel termin() {
        return new FremtidigFødsel(LocalDate.now(), LocalDate.now());
    }

    public static Fødsel fødsel() {
        return new Fødsel(
                1,
                singletonList(LocalDate.now().minusMonths(2)),
                null,
                null
                );
    }

    public static UttaksPeriode uttaksPeriode(LocalDate fom, LocalDate tom) {
        return new UttaksPeriode(
                fom,
                tom,
                List.of(),
                FEDREKVOTE,
                true,
                MorsAktivitet.ARBEID,
                true,
                new ProsentAndel(100.0d),
                null
        );
    }

    public static UttaksPeriode uttaksPeriode(VedleggReferanse... vedleggRefs) {
        return new UttaksPeriode(
                ukeDagNær(LocalDate.now().plusMonths(3)),
                ukeDagNær(LocalDate.now().plusMonths(4)),
                List.of(vedleggRefs),
                FEDREKVOTE,
                true,
                MorsAktivitet.ARBEID_OG_UTDANNING,
                true,
                new ProsentAndel(42d),
                null
        );
    }

    public static UttaksPeriode gradertPeriode(VedleggReferanse... vedleggRefs) {
        return new GradertUttaksPeriode(
                ukeDagNær(LocalDate.now().plusMonths(4)),
                ukeDagNær(LocalDate.now().plusMonths(5)),
                List.of(vedleggRefs),
                FEDREKVOTE,
                true,
                MorsAktivitet.ARBEID_OG_UTDANNING,
                true,
                null,
                new ProsentAndel(75d),
                true,
                Collections.singletonList("22222222222"),
                true,
                true,
                true,
                null
        );
    }

    public static OverføringsPeriode overføringsPeriode(VedleggReferanse... vedleggRefs) {
        return new OverføringsPeriode(ukeDagNær(LocalDate.now()), ukeDagNær(LocalDate.now().plusMonths(1)),
                Overføringsårsak.ALENEOMSORG, StønadskontoType.FEDREKVOTE, List.of(vedleggRefs));
    }

    public static OppholdsPeriode oppholdsPeriode(VedleggReferanse... vedleggRefs) {
        return new OppholdsPeriode(
                ukeDagNær(LocalDate.now().plusMonths(1)),
                ukeDagNær(LocalDate.now().plusMonths(2)),
                Oppholdsårsak.UTTAK_FEDREKVOTE_ANNEN_FORELDER, List.of(vedleggRefs));
    }

    public static UtsettelsesPeriode utsettelsesPeriode(VedleggReferanse... vedleggRefs) {
        return new UtsettelsesPeriode(
                ukeDagNær(LocalDate.now().plusMonths(2)),
                ukeDagNær(LocalDate.now().plusMonths(3)),
                true,
                UtsettelsesÅrsak.INSTITUSJONSOPPHOLD_BARNET,
                null,
                List.of(vedleggRefs)
        );
    }

    public static Fordeling fordeling(VedleggReferanse... vedleggRefs) {
        return new Fordeling(true, perioder(vedleggRefs), true);
    }

    public static Rettigheter rettigheter() {
        return new Rettigheter(true, true, true, false, false);
    }

    private static ValgfrittVedlegg opplastetVedlegg(String id, DokumentType type) {
        try {
            var vedleggMetaData = new VedleggMetaData(new VedleggReferanse(id), InnsendingsType.LASTET_OPP, type);
            var valgfrittVedlegg = new ValgfrittVedlegg(vedleggMetaData);
            valgfrittVedlegg.setInnhold(bytesFra("pdf/terminbekreftelse.pdf"));
            return valgfrittVedlegg;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private static ValgfrittVedlegg ikkeOpplastet(String id, DokumentType type) {
        try {
            var vedleggMetaData = new VedleggMetaData(new VedleggReferanse(id), InnsendingsType.SEND_SENERE, type);
            return new ValgfrittVedlegg(vedleggMetaData);
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

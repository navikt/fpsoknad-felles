package no.nav.foreldrepenger.common.domain.felles;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static no.nav.foreldrepenger.common.util.ResourceHandleUtil.bytesFra;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.BrukerRolle;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.domain.Søker;
import no.nav.foreldrepenger.common.domain.Søknad;
import no.nav.foreldrepenger.common.domain.engangsstønad.Engangsstønad;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.AnnenForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UkjentForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UtenlandskForelder;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Adopsjon;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.FremtidigFødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Fødsel;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.Omsorgsovertakelse;
import no.nav.foreldrepenger.common.domain.felles.relasjontilbarn.RelasjonTilBarn;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

public class TestUtils {
    public static boolean hasPdfSignature(byte[] bytes) {
        return bytes[0] == 0x25 &&
                bytes[1] == 0x50 &&
                bytes[2] == 0x44 &&
                bytes[3] == 0x46;
    }

    public static Søknad engangssøknad(boolean utland) {
        return engangssøknad(utland, termin());
    }

    public static Søknad engangssøknad(RelasjonTilBarn relasjon, boolean utland) {
        return engangssøknad(utland, relasjon);
    }

    public static Søknad engangssøknad(RelasjonTilBarn relasjon) {
        return engangssøknad(false, relasjon);
    }

    public static Søknad engangssøknad(Vedlegg... vedlegg) {
        return engangssøknad(true, termin(), vedlegg);
    }

    public static Søknad engangssøknad(boolean utland, RelasjonTilBarn relasjon, Vedlegg... vedlegg) {
        return new Søknad(LocalDate.now(), søker(), engangstønad(utland, relasjon), "Intet å tilføye", List.of(vedlegg));
    }

    public static Engangsstønad engangstønad(boolean utland, RelasjonTilBarn relasjon) {
        return new Engangsstønad(opphold(utland), relasjon);
    }

    public static Utenlandsopphold.Opphold utenlandsopphold() {
        return new Utenlandsopphold.Opphold(CountryCode.SE, varighet());
    }

    public static NorskForelder norskForelder() {
        return new NorskForelder(fnr(), "Far Farsen");
    }

    public static UtenlandskForelder utenlandskForelder() {
        return new UtenlandskForelder("123456", CountryCode.SE, "Far Farsen");
    }

    public static Utenlandsopphold opphold() {
        return oppholdBareINorge();
    }

    public static Utenlandsopphold opphold(boolean utland) {
        if (utland) {
            return oppholdUtlandet();
        }
        return oppholdBareINorge();
    }

    public static Utenlandsopphold oppholdBareINorge() {
        return new Utenlandsopphold(List.of());
    }

    public static Utenlandsopphold oppholdUtlandet() {
        var tidligereOpphold = List.of(
                new Utenlandsopphold.Opphold(CountryCode.AT, new LukketPeriode(LocalDate.now().minusYears(1), LocalDate.now().minusMonths(6).minusDays(1))),
                new Utenlandsopphold.Opphold(CountryCode.FI, new LukketPeriode(LocalDate.now().minusMonths(6), LocalDate.now()))
        );
        var fremtidigOpphold = List.of(
                new Utenlandsopphold.Opphold(CountryCode.GR, new LukketPeriode(LocalDate.now(), LocalDate.now().plusMonths(6))),
                new Utenlandsopphold.Opphold(CountryCode.DE, new LukketPeriode(LocalDate.now().plusYears(1), LocalDate.now().plusYears(1).plusMonths(6)))
        );
        return new Utenlandsopphold(Stream.concat(tidligereOpphold.stream(), fremtidigOpphold.stream()).toList());
    }

    public static Omsorgsovertakelse omsorgsovertakelse() {
        return new Omsorgsovertakelse(nå(), forrigeMåned());
    }

    public static PåkrevdVedlegg påkrevdVedlegg(String id) {
        return påkrevdVedlegg(id, "pdf/terminbekreftelse.pdf");
    }

    public static ValgfrittVedlegg valgfrittVedlegg(String id, InnsendingsType type) {
        return valgfrittVedlegg(id, type, "pdf/terminbekreftelse.pdf");
    }

    public static PåkrevdVedlegg påkrevdVedlegg(String id, String name) {
        var vedleggMetaData = new VedleggMetaData(new VedleggReferanse(id), InnsendingsType.LASTET_OPP, DokumentType.I000062);
        var påkrevdVedlegg = new PåkrevdVedlegg(vedleggMetaData);
        påkrevdVedlegg.setInnhold(bytesFra(name));
        return påkrevdVedlegg;
    }

    static ValgfrittVedlegg valgfrittVedlegg(String id, InnsendingsType type, String name) {
        var vedleggMetaData = new VedleggMetaData(new VedleggReferanse(id), type, DokumentType.I000062);
        var valgfrittVedlegg = new ValgfrittVedlegg(vedleggMetaData);
        valgfrittVedlegg.setInnhold(bytesFra(name));
        return valgfrittVedlegg;
    }

    public static Adopsjon adopsjon() {
        return new Adopsjon(1, nå(), false, false, emptyList(), nå(), listeMedNå());
    }

    public static RelasjonTilBarn fødsel() {
        return fødsel(forrigeMåned());
    }

    public static RelasjonTilBarn fødsel(LocalDate date) {
        return new Fødsel(
                1,
                singletonList(date),
                date,
                null
        );
    }

    public static List<Utenlandsopphold.Opphold> framtidigOppHoldIUtlandet() {
        List<Utenlandsopphold.Opphold> opphold = new ArrayList<>();
        opphold.add(new Utenlandsopphold.Opphold(CountryCode.GR,
                new LukketPeriode(LocalDate.now(), LocalDate.now().plusMonths(6))));
        opphold.add(new Utenlandsopphold.Opphold(CountryCode.DE,
                new LukketPeriode(LocalDate.now().plusYears(1), LocalDate.now().plusYears(1).plusMonths(6))));
        return opphold;
    }

    public static List<Utenlandsopphold.Opphold> framtidigOppholdINorge() {
        return emptyList();
    }

    public static String serialize(Object obj, boolean print, ObjectMapper mapper) {
        try {
            String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            return print ? printSerialized(serialized) : serialized;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String printSerialized(String serialized) {
        return serialized;
    }

    public static Søker søker() {
        return new Søker(BrukerRolle.MOR, Målform.standard());
    }

    public static RelasjonTilBarn termin() {
        return new FremtidigFødsel(nesteMåned(), forrigeMåned());
    }

    public static LukketPeriode varighet() {
        return new LukketPeriode(nå(), nesteMåned());
    }

    public static LocalDate nesteMåned() {
        return nå().plus(enMåned());
    }

    static LocalDate forrigeMåned() {
        return nå().minusMonths(1);
    }

    static Period enMåned() {
        return måned(1);
    }

    static Period måned(int n) {
        return Period.ofMonths(n);
    }

    public static LocalDate nå() {
        return LocalDate.now();
    }

    public static List<LocalDate> listeMedNå() {
        return singletonList(nå());
    }

    static LocalDate ettÅrSiden() {
        return LocalDate.now().minus(Period.ofYears(1));
    }

    public static AktørId aktoer() {
        return new AktørId("11111111111111111");
    }

    static Fødselsnummer fnr() {
        return new Fødselsnummer("11111111111");
    }

    public static Navn navnUtenMellomnavn() {
        return new Navn("Mor", null, "Monsen");
    }

    public static AnnenForelder ukjentForelder() {
        return new UkjentForelder();
    }

    public static Person person() {
        return new Person(new AktørId("42"), new Fødselsnummer("11111111111"),
                LocalDate.now().minusYears(25), new Navn("Mor", "Mellommor", "Morsen"),
                Kjønn.K, Målform.NN,
                new Bankkonto("2000.20.20000", "Store Fiskerbank"), null, new Sivilstand(Sivilstand.SivilstandType.GIFT));
    }
}

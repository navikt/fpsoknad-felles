package no.nav.foreldrepenger.common.innsyn;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static no.nav.foreldrepenger.common.innsyn.svp.SvpPeriode.ResultatType.AVSLAG_SØKNADSFRIST;
import static no.nav.foreldrepenger.common.innsyn.svp.SvpPeriode.ResultatType.INNVILGET;
import static no.nav.foreldrepenger.common.innsyn.svp.TilretteleggingType.DELVIS;
import static no.nav.foreldrepenger.common.innsyn.svp.TilretteleggingType.HEL;
import static no.nav.foreldrepenger.common.mapper.DefaultJsonMapper.MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.innsyn.svp.AktivitetVedtak;
import no.nav.foreldrepenger.common.innsyn.svp.OppholdPeriode;
import no.nav.foreldrepenger.common.innsyn.svp.SvpPeriode;
import no.nav.foreldrepenger.common.innsyn.svp.SvpPeriode.Utbetalingsgrad;
import no.nav.foreldrepenger.common.innsyn.svp.SvpSak;
import no.nav.foreldrepenger.common.innsyn.svp.Søknad;
import no.nav.foreldrepenger.common.innsyn.svp.Tilrettelegging;
import no.nav.foreldrepenger.common.innsyn.svp.TilretteleggingPeriode;
import no.nav.foreldrepenger.common.innsyn.svp.Vedtak;
import no.nav.foreldrepenger.common.innsyn.svp.ÅpenBehandling;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class SakerV2SerialiseringTest extends SerializationTestBase {

    private final ObjectMapper mapper = MAPPER;

    @Test
    void sakerV2ForeldrepengerRoundtripTest() throws Exception {
        var saksnummer = new Saksnummer("123");
        var familieHendelse = new Familiehendelse(of(2021, 12, 6),
                of(2021, 12, 5), 1, of(2021, 12, 12));
        var vedtakPerioder = new UttakPeriode(of(2021, 12, 1),
                of(2022, 3, 31),
                KontoType.FORELDREPENGER,
                new UttakPeriodeResultat(true, true, true, UttakPeriodeResultat.Årsak.ANNET),
                UtsettelseÅrsak.BARN_INNLAGT,
                OppholdÅrsak.MØDREKVOTE_ANNEN_FORELDER,
                OverføringÅrsak.ALENEOMSORG,
                new Gradering(BigDecimal.valueOf(50L), new Aktivitet(Aktivitet.Type.ORDINÆRT_ARBEID,
                        new Arbeidsgiver("123", Arbeidsgiver.ArbeidsgiverType.ORGANISASJON))),
                MorsAktivitet.INNLAGT,
                new SamtidigUttak(BigDecimal.valueOf(30L)),
                false);
        var åpenBehandling = new FpÅpenBehandling(BehandlingTilstand.UNDER_BEHANDLING, List.of(new UttakPeriode(of(2021, 11, 1),
                of(2021, 11, 13), KontoType.FORELDREPENGER, null, UtsettelseÅrsak.FRI, OppholdÅrsak.FEDREKVOTE_ANNEN_FORELDER,
                OverføringÅrsak.SYKDOM_ANNEN_FORELDER, new Gradering(BigDecimal.valueOf(10), new Aktivitet(Aktivitet.Type.ORDINÆRT_ARBEID,
                new Arbeidsgiver("123", Arbeidsgiver.ArbeidsgiverType.ORGANISASJON))), MorsAktivitet.ARBEID,
                new SamtidigUttak(BigDecimal.valueOf(10)), true)));
        var fpVedtak = new FpVedtak(List.of(vedtakPerioder));
        var fpSak = new FpSak(saksnummer, false,false, false, false, false, false, true,
                RettighetType.ALENEOMSORG, new Person(new Fødselsnummer("42"), null), familieHendelse, fpVedtak, åpenBehandling, Set.of(new Person(new Fødselsnummer("1"), new AktørId("2"))),
                Dekningsgrad.ÅTTI, LocalDateTime.now());
        var saker = new Saker(Set.of(fpSak), Set.of(), Set.of());

        test(saker, true);
    }

    @Test
    void sakerV2SvangerskapspengerRoundtripTest() throws Exception {
        var saksnummer = new Saksnummer("123");
        var fødselsdato = now().plusWeeks(10);
        var familieHendelse = new Familiehendelse(fødselsdato,
                fødselsdato.plusWeeks(2), 1, null);
        var aktivitet = new Aktivitet(Aktivitet.Type.ORDINÆRT_ARBEID,
                new Arbeidsgiver("1", Arbeidsgiver.ArbeidsgiverType.ORGANISASJON));
        var arbeidstidprosent = new Arbeidstidprosent(BigDecimal.valueOf(50));
        var tilrettelegging1 = new Tilrettelegging(aktivitet, now(), "risiko", "tiltak",
                Set.of(new TilretteleggingPeriode(now(), now(), DELVIS, arbeidstidprosent)));
        var tilrettelegging2 = new Tilrettelegging(aktivitet, now(), "risiko", "tiltak",
                Set.of(new TilretteleggingPeriode(now().plusWeeks(1), now().plusWeeks(1), HEL, new Arbeidstidprosent(BigDecimal.ZERO))));
        var søknad = new Søknad(Set.of(tilrettelegging1, tilrettelegging2));
        var vedtakPeriode1 = new SvpPeriode(now(), now(), DELVIS, arbeidstidprosent, AVSLAG_SØKNADSFRIST,
                new Utbetalingsgrad(BigDecimal.valueOf(0)));
        var vedtakPeriode2 = new SvpPeriode(now().plusWeeks(2), now().plusWeeks(2), HEL, new Arbeidstidprosent(BigDecimal.ZERO), INNVILGET,
                new Utbetalingsgrad(BigDecimal.valueOf(100)));
        var oppholdPeriode = new OppholdPeriode(now().plusWeeks(1), now().plusWeeks(1), OppholdPeriode.Årsak.FERIE);
        var vedtak = new Vedtak(Set.of(new AktivitetVedtak(aktivitet, now(), Set.of(vedtakPeriode1, vedtakPeriode2, oppholdPeriode),
                AktivitetVedtak.AvslutningÅrsak.AVSLAG_OVERGANG_FORELDREPENGER)));
        var svpSak = new SvpSak(saksnummer, familieHendelse, true, new ÅpenBehandling(BehandlingTilstand.UNDER_BEHANDLING, søknad),
                vedtak, LocalDateTime.now());
        var saker = new Saker(Set.of(), Set.of(), Set.of(svpSak));

        roundtripTest(saker);
    }

    @Test
    void sakerV2EngangsstønadRoundtripTest() throws Exception {
        var saksnummer = new Saksnummer("123");
        var familieHendelse = new Familiehendelse(of(2021, 12, 6),
                of(2021, 12, 5), 1, of(2021, 12, 12));
        var esSaker = new EsSak(saksnummer, familieHendelse, true, new EsÅpenBehandling(BehandlingTilstand.UNDER_BEHANDLING), false, LocalDateTime.now());
        var saker = new Saker(Set.of(), Set.of(esSaker), Set.of());

        roundtripTest(saker);
    }

    private void roundtripTest(Object object) throws IOException {
        assertEquals(object, mapper.readValue(write(object), object.getClass()));
    }

    private String write(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}

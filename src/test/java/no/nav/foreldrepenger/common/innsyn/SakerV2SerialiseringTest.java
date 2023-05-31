package no.nav.foreldrepenger.common.innsyn;

import static no.nav.foreldrepenger.common.mapper.DefaultJsonMapper.MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class SakerV2SerialiseringTest extends SerializationTestBase {

    private final ObjectMapper mapper = MAPPER;

    @Test
    void sakerV2ForeldrepengerRoundtripTest() throws Exception {
        var saksnummer = new Saksnummer("123");
        var familieHendelse = new Familiehendelse(LocalDate.of(2021, 12, 6),
                LocalDate.of(2021, 12, 5), 1, LocalDate.of(2021, 12, 12));
        var vedtakPerioder = new UttakPeriode(LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 3, 31),
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
        var åpenBehandling = new FpÅpenBehandling(BehandlingTilstand.UNDER_BEHANDLING, List.of(new UttakPeriode(LocalDate.of(2021, 11, 1),
                LocalDate.of(2021, 11, 13), KontoType.FORELDREPENGER, null, UtsettelseÅrsak.FRI, OppholdÅrsak.FEDREKVOTE_ANNEN_FORELDER,
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
        var familieHendelse = new Familiehendelse(LocalDate.of(2021, 12, 6),
                LocalDate.of(2021, 12, 5), 1, LocalDate.of(2021, 12, 12));
        var svpSak = new SvpSak(saksnummer, familieHendelse, true, new SvpÅpenBehandling(BehandlingTilstand.UNDER_BEHANDLING), LocalDateTime.now());
        var saker = new Saker(Set.of(), Set.of(), Set.of(svpSak));

        roundtripTest(saker);
    }

    @Test
    void sakerV2EngangsstønadRoundtripTest() throws Exception {
        var saksnummer = new Saksnummer("123");
        var familieHendelse = new Familiehendelse(LocalDate.of(2021, 12, 6),
                LocalDate.of(2021, 12, 5), 1, LocalDate.of(2021, 12, 12));
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

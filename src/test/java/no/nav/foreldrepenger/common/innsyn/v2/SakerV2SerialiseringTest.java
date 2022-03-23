package no.nav.foreldrepenger.common.innsyn.v2;

import static no.nav.foreldrepenger.common.mapper.DefaultJsonMapper.MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.innsyn.v2.persondetaljer.AktørId;
import no.nav.foreldrepenger.common.innsyn.v2.persondetaljer.Kjønn;
import no.nav.foreldrepenger.common.innsyn.v2.persondetaljer.Person;

class SakerV2SerialiseringTest {

    private ObjectMapper mapper = MAPPER;

    private final static AnnenPart annenPart = new AnnenPart(new AktørId("42"));
    private final static AktørId barn = new AktørId("1");

    @Test
    void annenPartRoundtripTest() throws Exception {
        roundtripTest(annenPart);
    }

    @Test
    void annenPartPersonRoundtripTest() throws IOException {
        var person = new Person(new Fødselsnummer("12345678901"), "Navn", null, "Navnæsen", Kjønn.K, LocalDate.now().minusDays(1));
        var annenPartPerson = new AnnenPart(person);
        roundtripTest(annenPartPerson);
    }

    @Test
    public void sakerV2ForeldrepengerRoundtripTest() throws Exception {
        var saksnummer = new Saksnummer("123");
        var familieHendelse = new Familiehendelse(LocalDate.of(2021, 12, 6),
                LocalDate.of(2021, 12, 5), 1, LocalDate.of(2021, 12, 12));
        var vedtakPerioder = new VedtakPeriode(LocalDate.of(2021, 12, 1),
                LocalDate.of(2022, 3, 31),
                KontoType.FORELDREPENGER,
                new VedtakPeriodeResultat(true),
                UtsettelseÅrsak.BARN_INNLAGT,
                OppholdÅrsak.MØDREKVOTE_ANNEN_FORELDER,
                OverføringÅrsak.ALENEOMSORG,
                new Gradering(BigDecimal.valueOf(50L)),
                MorsAktivitet.INNLAGT,
                new SamtidigUttak(BigDecimal.valueOf(30L)),
                false);
        var åpenBehandling = new FpÅpenBehandling(BehandlingTilstand.UNDER_BEHANDLING, Set.of(new Søknadsperiode(LocalDate.of(2021, 11, 1),
                LocalDate.of(2021, 11, 13), KontoType.FORELDREPENGER)));
        var fpVedtak = new FpVedtak(List.of(vedtakPerioder));
        var fpSak = new FpSak(saksnummer, false, false, false, false,false,
                RettighetType.ALENEOMSORG, annenPart, familieHendelse, fpVedtak, åpenBehandling, Set.of(barn),
                Dekningsgrad.ÅTTI);
        var saker = new Saker(Set.of(fpSak), Set.of(), Set.of());

        roundtripTest(saker);
    }

    private void roundtripTest(Object object) throws IOException {
        assertEquals(object, mapper.readValue(write(object), object.getClass()));
    }

    private String write(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
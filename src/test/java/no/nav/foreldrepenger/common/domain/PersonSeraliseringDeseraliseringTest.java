package no.nav.foreldrepenger.common.domain;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.domain.felles.AnnenPart;
import no.nav.foreldrepenger.common.domain.felles.Bankkonto;
import no.nav.foreldrepenger.common.domain.felles.Kjønn;
import no.nav.foreldrepenger.common.domain.felles.Person;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class PersonSeraliseringDeseraliseringTest extends SerializationTestBase {

    @Test
    void PersonRoundTripTest() {
        var annenPart = new AnnenPart(new Fødselsnummer("44444455555"), AktørId.valueOf("9944444455555"),
                new Navn("Annen", "", "Part"), LocalDate.now().minusYears(20));
        var barn = new Barn(new Fødselsnummer("33333344444"), LocalDate.now().minusMonths(1),
                new Navn("Barn", "", "Barnason"), Kjønn.M, annenPart);
        var person = Person.builder()
                .aktørId(AktørId.valueOf("9911111122222"))
                .fnr(new Fødselsnummer("111111222222"))
                .fødselsdato(LocalDate.now().minusYears(25))
                .navn(new Navn("Ola", "Nord", "Mann"))
                .kjønn(Kjønn.K)
                .målform(Målform.NB)
                .land(CountryCode.NO)
                .bankkonto(Bankkonto.UKJENT)
                .barn(Set.of(barn))
                .build();

        test(person);
    }
}
package no.nav.foreldrepenger.common.domain;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.nav.foreldrepenger.common.domain.felles.AnnenPart;
import no.nav.foreldrepenger.common.domain.felles.Bankkonto;
import no.nav.foreldrepenger.common.domain.felles.Kjønn;
import no.nav.foreldrepenger.common.domain.felles.Person;
import no.nav.foreldrepenger.common.domain.felles.Sivilstand;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;
import no.nav.foreldrepenger.common.util.SerializationTestBase;

class PersonSeraliseringDeseraliseringTest extends SerializationTestBase {

    @Test
    void PersonRoundTripTest() {
        var annenPart = new AnnenPart(new Fødselsnummer("44444455555"), new AktørId("9944444455555"),
                new Navn("Annen", "", "Part"), LocalDate.now().minusYears(20));
        var barn = new Barn(new Fødselsnummer("33333344444"), LocalDate.now().minusMonths(1), LocalDate.now(),
                new Navn("Barn", "", "Barnason"), Kjønn.M, annenPart);
        var person = new Person(
                new AktørId("9911111122222"),
                new Fødselsnummer("111111222222"),
                LocalDate.now().minusYears(25),
                new Navn("Ola", "Nord", "Mann"),
                Kjønn.K,
                Målform.NB,
                Bankkonto.UKJENT,
                List.of(barn),
                new Sivilstand(Sivilstand.Type.GIFT));

        test(person);
    }
}

package no.nav.foreldrepenger.common.oppslag;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.domain.felles.Person;
import no.nav.foreldrepenger.common.http.Pingable;

public interface Oppslag extends Pingable {
    Person person();

    AktørId aktørId();

    AktørId aktørId(Fødselsnummer fnr);

    Fødselsnummer fnr(AktørId aktørId);

    Navn navn(String id);

}
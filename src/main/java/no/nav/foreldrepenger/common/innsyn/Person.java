package no.nav.foreldrepenger.common.innsyn;

import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;


public record Person(Fødselsnummer fnr, AktørId aktørId) {
}

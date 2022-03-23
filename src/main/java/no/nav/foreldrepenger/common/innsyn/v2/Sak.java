package no.nav.foreldrepenger.common.innsyn.v2;

import java.util.Set;

interface Sak {

    Saksnummer saksnummer();

    Familiehendelse familiehendelse();

    Set<PersonDetaljer> barn();

    boolean gjelderAdopsjon();

    boolean sakAvsluttet();

}

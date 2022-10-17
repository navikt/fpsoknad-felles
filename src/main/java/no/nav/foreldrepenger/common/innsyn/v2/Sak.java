package no.nav.foreldrepenger.common.innsyn.v2;

import java.time.LocalDate;
import java.util.Set;

interface Sak {

    Saksnummer saksnummer();

    Familiehendelse familiehendelse();

    Set<PersonDetaljer> barn();

    boolean gjelderAdopsjon();

    boolean sakAvsluttet();

    LocalDate sisteSøknadMottattDato();

}

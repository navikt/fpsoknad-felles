package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDateTime;

public interface Sak {

    Saksnummer saksnummer();

    Familiehendelse familiehendelse();

    boolean sakAvsluttet();

    LocalDateTime oppdatertTidspunkt();

}

package no.nav.foreldrepenger.common.innsyn.v2;

import java.time.LocalDate;
import java.util.Set;

public record EsSak(Saksnummer saksnummer,
                    LocalDate sisteSøknadMottattDato,
                    Familiehendelse familiehendelse,
                    EsVedtak gjeldendeVedtak,
                    EsÅpenBehandling åpenBehandling,
                    Set<PersonDetaljer> barn,
                    boolean sakAvsluttet,
                    boolean gjelderAdopsjon) implements Sak {
}

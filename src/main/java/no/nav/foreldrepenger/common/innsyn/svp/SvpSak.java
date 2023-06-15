package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDateTime;

import no.nav.foreldrepenger.common.innsyn.Familiehendelse;
import no.nav.foreldrepenger.common.innsyn.Sak;
import no.nav.foreldrepenger.common.innsyn.Saksnummer;

public record SvpSak(Saksnummer saksnummer,
                     Familiehendelse familiehendelse,
                     boolean sakAvsluttet,
                     ÅpenBehandling åpenBehandling,
                     Vedtak gjeldendeVedtak,
                     LocalDateTime oppdatertTidspunkt) implements Sak {
}

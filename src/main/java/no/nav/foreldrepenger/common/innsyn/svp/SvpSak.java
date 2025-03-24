package no.nav.foreldrepenger.common.innsyn.svp;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import no.nav.foreldrepenger.common.innsyn.Familiehendelse;
import no.nav.foreldrepenger.common.innsyn.Sak;
import no.nav.foreldrepenger.common.innsyn.Saksnummer;

public record SvpSak(@NotNull Saksnummer saksnummer,
                     @NotNull Familiehendelse familiehendelse,
                     @NotNull boolean sakAvsluttet,
                     ÅpenBehandling åpenBehandling,
                     Vedtak gjeldendeVedtak,
                     @NotNull LocalDateTime oppdatertTidspunkt) implements Sak {
}

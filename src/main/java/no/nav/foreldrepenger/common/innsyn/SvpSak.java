package no.nav.foreldrepenger.common.innsyn;

import java.time.LocalDateTime;
import java.util.Objects;

public record SvpSak(Saksnummer saksnummer,
                     Familiehendelse familiehendelse,
                     boolean sakAvsluttet,
                     SvpÅpenBehandling åpenBehandling,
                     LocalDateTime oppdatertTidspunkt) implements Sak {

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SvpSak svpSak = (SvpSak) o;
        return sakAvsluttet == svpSak.sakAvsluttet && Objects.equals(saksnummer, svpSak.saksnummer) && Objects.equals(familiehendelse,
                svpSak.familiehendelse) && Objects.equals(åpenBehandling, svpSak.åpenBehandling);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saksnummer, familiehendelse, sakAvsluttet, åpenBehandling);
    }
}

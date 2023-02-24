package no.nav.foreldrepenger.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.felles.AnnenPart;
import no.nav.foreldrepenger.common.innsyn.Behandling;
import no.nav.foreldrepenger.common.innsyn.FagsakStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public record Sak(String saksnummer,
                  FagsakStatus status,
                  String behandlingTema,
                  AktørId aktørId,
                  AnnenPart annenPart,
                  List<AktørId> aktørIdBarn,
                  List<Behandling> behandlinger,
                  LocalDateTime opprettet,
                  LocalDateTime endret,
                  boolean mottattEndringssøknad) {


    @JsonCreator
    public Sak {
        aktørIdBarn = Optional.ofNullable(aktørIdBarn).orElse(emptyList());
        behandlinger = Optional.ofNullable(behandlinger).orElse(emptyList());
    }
}

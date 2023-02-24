package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public record Svangerskapspenger(LocalDate termindato,
                                 /*@Nullable*/ LocalDate fødselsdato,
                                 Medlemsskap medlemsskap,
                                 Opptjening opptjening,
                                 List<Tilrettelegging> tilrettelegging) implements Ytelse {
    @JsonCreator
    public Svangerskapspenger {
        tilrettelegging = Optional.ofNullable(tilrettelegging).orElse(emptyList());
    }
}

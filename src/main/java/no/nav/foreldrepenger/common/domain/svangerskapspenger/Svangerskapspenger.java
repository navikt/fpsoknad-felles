package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.OppholdIUtlandet;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging;

public record Svangerskapspenger(LocalDate termindato,
                                 /*@Nullable*/ LocalDate fødselsdato,
                                 @Valid @Deprecated Medlemsskap medlemsskap,
                                 @Valid OppholdIUtlandet utenlandsopphold,
                                 @Valid Opptjening opptjening,
                                 List<@Valid Tilrettelegging> tilrettelegging) implements Ytelse {
    @JsonCreator
    public Svangerskapspenger {
        tilrettelegging = Optional.ofNullable(tilrettelegging).orElse(emptyList());
    }
}

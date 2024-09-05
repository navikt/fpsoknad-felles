package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging;

public record Svangerskapspenger(LocalDate termindato,
                                 /*@Nullable*/ LocalDate fødselsdato,
                                 @Valid Utenlandsopphold utenlandsopphold,
                                 @Valid Opptjening opptjening,
                                 List<@Valid Tilrettelegging> tilrettelegging,
                                 List<@Valid AvtaltFerie> avtaltFerie) implements Ytelse {
    @JsonCreator
    public Svangerskapspenger {
        tilrettelegging = Optional.ofNullable(tilrettelegging).orElse(emptyList());
    }

    @JsonIgnore
    public LocalDate getTidligstDatoForTilrettelegging() {
        return tilrettelegging.stream()
                .map(Tilrettelegging::getBehovForTilretteleggingFom)
                .min(LocalDate::compareTo)
                .orElse(Optional.ofNullable(fødselsdato).orElse(termindato));
    }
}

package no.nav.foreldrepenger.common.domain.svangerskapspenger;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import no.nav.foreldrepenger.common.domain.Ytelse;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Utenlandsopphold;
import no.nav.foreldrepenger.common.domain.felles.opptjening.Opptjening;

public record Svangerskapspenger(@NotNull LocalDate termindato,
                                 LocalDate fødselsdato,
                                 @Valid Utenlandsopphold utenlandsopphold,
                                 @Valid Opptjening opptjening,
                                 @Valid @Size(min = 1, max = 20) List<@Valid @NotNull Tilretteleggingbehov> tilretteleggingbehov,
                                 @Valid @Size(max = 20) List<@Valid @NotNull AvtaltFerie> avtaltFerie) implements Ytelse {

    @JsonCreator
    public Svangerskapspenger {
        avtaltFerie = Optional.ofNullable(avtaltFerie).orElse(emptyList());
    }

    @JsonIgnore
    public LocalDate getTidligstDatoForTilrettelegging() {
        return tilretteleggingbehov.stream()
                .map(Tilretteleggingbehov::behovForTilretteleggingFom)
                .min(LocalDate::compareTo)
                .orElse(Optional.ofNullable(fødselsdato).orElse(termindato));
    }
}

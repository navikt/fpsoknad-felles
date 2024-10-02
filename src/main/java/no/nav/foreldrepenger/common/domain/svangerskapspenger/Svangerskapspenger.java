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
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.Tilrettelegging;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilretteleggingsbehov.Tilretteleggingbehov;

public record Svangerskapspenger(LocalDate termindato,
                                 /*@Nullable*/ LocalDate fødselsdato,
                                 @Valid Utenlandsopphold utenlandsopphold,
                                 @Valid Opptjening opptjening,
                                 List<@Valid Tilrettelegging> tilrettelegging,
                                 @Valid @Size(max = 20) List<@Valid @NotNull Tilretteleggingbehov> tilretteleggingbehov,
                                 List<@Valid AvtaltFerie> avtaltFerie) implements Ytelse {

    @JsonCreator
    public Svangerskapspenger {
        tilrettelegging = Optional.ofNullable(tilrettelegging).orElse(emptyList());
        tilretteleggingbehov = Optional.ofNullable(tilretteleggingbehov).orElse(emptyList());
    }

    @JsonIgnore
    public LocalDate getTidligstDatoForTilrettelegging() {
        if (!tilretteleggingbehov.isEmpty()) {
            return tilretteleggingbehov.stream()
                    .map(Tilretteleggingbehov::behovForTilretteleggingFom)
                    .min(LocalDate::compareTo)
                    .orElse(Optional.ofNullable(fødselsdato).orElse(termindato));
        } else {
            return tilrettelegging.stream()
                    .map(Tilrettelegging::getBehovForTilretteleggingFom)
                    .min(LocalDate::compareTo)
                    .orElse(Optional.ofNullable(fødselsdato).orElse(termindato));
        }
    }
}

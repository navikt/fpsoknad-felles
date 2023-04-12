package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SelvstendigNæringsdrivende(@NotNull @Pattern(regexp = FRITEKST) String risikoFaktorer,
                                         @NotNull @Pattern(regexp = FRITEKST) String tilretteleggingstiltak) implements Arbeidsforhold {
}

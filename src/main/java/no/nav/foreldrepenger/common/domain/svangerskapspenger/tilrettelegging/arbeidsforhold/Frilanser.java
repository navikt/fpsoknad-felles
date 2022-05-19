package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record Frilanser(@NotNull @Pattern(regexp = FRITEKST) String risikoFaktorer,
                        @NotNull @Pattern(regexp = FRITEKST) String tilretteleggingstiltak) implements Arbeidsforhold {
}

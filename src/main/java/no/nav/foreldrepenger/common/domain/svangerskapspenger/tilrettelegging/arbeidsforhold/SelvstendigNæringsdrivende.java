package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class SelvstendigNæringsdrivende extends Arbeidsforhold {
    @NotNull
    @Pattern(regexp = FRITEKST)
    private final String risikoFaktorer;
    @NotNull
    @Pattern(regexp = FRITEKST)
    private final String tilretteleggingstiltak;
}

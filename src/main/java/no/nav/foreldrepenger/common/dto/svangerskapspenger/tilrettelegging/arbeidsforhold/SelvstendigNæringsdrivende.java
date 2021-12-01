package no.nav.foreldrepenger.common.dto.svangerskapspenger.tilrettelegging.arbeidsforhold;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public final class SelvstendigNæringsdrivende extends Arbeidsforhold {
    @NotNull
    private final String risikoFaktorer;
    @NotNull
    private final String tilretteleggingstiltak;
}

package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nav.foreldrepenger.common.domain.validation.annotations.Orgnr;

@Data
@EqualsAndHashCode(callSuper = false)
public class Virksomhet extends Arbeidsforhold {

    @Orgnr
    @NotNull
    public final String orgnr;

    @JsonCreator
    public Virksomhet(@JsonProperty("orgnr") String orgnr) {
        this.orgnr = orgnr;
    }

}

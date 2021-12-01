package no.nav.foreldrepenger.common.dto.svangerskapspenger.tilrettelegging.arbeidsforhold;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nav.foreldrepenger.common.dto.Orgnummer;

@Data
@EqualsAndHashCode(callSuper = false)
public final class Virksomhet extends Arbeidsforhold {

    @NotNull
    public final Orgnummer orgnr;

    public Virksomhet(String orgnr) {
        this(new Orgnummer(orgnr));
    }

    @JsonCreator
    public Virksomhet(@JsonProperty("orgnr") Orgnummer orgnr) {
        this.orgnr = orgnr;
    }

}

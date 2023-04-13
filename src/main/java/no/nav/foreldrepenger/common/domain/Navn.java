package no.nav.foreldrepenger.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

@JsonPropertyOrder({"fornavn", "mellomnavn", "etternavn"})
public record Navn(@Pattern(regexp = FRITEKST) String fornavn,
                   @Pattern(regexp = FRITEKST) String mellomnavn,
                   @Pattern(regexp = FRITEKST) String etternavn) {

    @JsonIgnore
    public String navn() {
        return Stream.of(fornavn, mellomnavn, etternavn)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return "Navn{" + "fornavn='" + mask(fornavn) + '\'' + ", mellomnavn='" + mask(mellomnavn) + '\'' + ", etternavn='" + mask(
                etternavn) + '\'' + '}';
    }
}

package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;
import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.Joiner;

@JsonPropertyOrder({"fornavn", "mellomnavn", "etternavn"})
public record Navn(@Pattern(regexp = FRITEKST) String fornavn, @Pattern(regexp = FRITEKST) String mellomnavn,
                   @Pattern(regexp = FRITEKST) String etternavn) {

    @JsonIgnore
    public String navn() {
        return Joiner.on(' ').skipNulls().join(fornavn, mellomnavn, etternavn);
    }

    @Override
    public String toString() {
        return "Navn{" + "fornavn='" + mask(fornavn) + '\'' + ", mellomnavn='" + mask(mellomnavn) + '\'' + ", etternavn='" + mask(
                etternavn) + '\'' + '}';
    }
}

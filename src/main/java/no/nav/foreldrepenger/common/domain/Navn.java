package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.util.StringUtil.mask;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.Joiner;

@JsonPropertyOrder({ "fornavn", "mellomnavn", "etternavn" })
public record Navn(String fornavn, String mellomnavn, String etternavn) {

    @JsonIgnore
    public String navn() {
        return Joiner.on(' ').skipNulls().join(fornavn, mellomnavn, etternavn);
    }

    @Override
    public String toString() {
        return "Navn{" +
                "fornavn='" + mask(fornavn) + '\'' +
                ", mellomnavn='" + mask(mellomnavn) + '\'' +
                ", etternavn='" +mask(etternavn) + '\'' +
                '}';
    }
}

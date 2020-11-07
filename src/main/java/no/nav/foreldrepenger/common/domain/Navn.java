package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.util.StringUtil.join;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@JsonPropertyOrder({ "fornavn", "mellomnavn", "etternavn", "kjønn" })
public class Navn {

    private final String fornavn;
    private final String mellomnavn;
    private final String etternavn;
    @Exclude
    private final Kjønn kjønn;

    @JsonCreator
    public Navn(@JsonProperty("fornavn") String fornavn, @JsonProperty("mellomnavn") String mellomnavn,
            @JsonProperty("etternavn") String etternavn, @JsonProperty("kjønn") Kjønn kjønn) {
        this.fornavn = fornavn;
        this.mellomnavn = mellomnavn;
        this.etternavn = etternavn;
        this.kjønn = kjønn;
    }

    public String navn() {
        return join(" ", fornavn, mellomnavn, etternavn);
    }
}

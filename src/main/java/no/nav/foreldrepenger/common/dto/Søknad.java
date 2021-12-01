package no.nav.foreldrepenger.common.dto;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import no.nav.foreldrepenger.common.dto.felles.Vedlegg;

@JsonPropertyOrder({ "mottattdato", "søker", "ytelse", "begrunnelseForSenSøknad", "tilleggsopplysninger", "vedlegg" })
public class Søknad {

    private final LocalDate mottattdato;
    private final Søker søker;
    private final Ytelse ytelse;
    private String tilleggsopplysninger;
    private final List<Vedlegg> vedlegg;

    @JsonCreator
    public Søknad(@JsonProperty("mottattdato") LocalDate mottattdato, @JsonProperty("søker") Søker søker,
                  @JsonProperty("ytelse") Ytelse ytelse, @JsonProperty("tilleggsopplysninger") String tilleggsopplysninger,
                  @JsonProperty("vedlegg") List<Vedlegg> vedlegg) {
        this.mottattdato = mottattdato;
        this.søker = søker;
        this.ytelse = ytelse;
        this.tilleggsopplysninger = tilleggsopplysninger;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}

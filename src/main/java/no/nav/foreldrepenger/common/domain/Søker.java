package no.nav.foreldrepenger.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static no.nav.foreldrepenger.common.oppslag.dkif.Målform.standard;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Søker(@NotNull(message = "Søknadsrolle må være satt") BrukerRolle søknadsRolle,
                    @JsonProperty("språkkode") Målform målform) {

    @JsonCreator
    public Søker {
        målform = Optional.ofNullable(målform).orElse(standard());
    }
}

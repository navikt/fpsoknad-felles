package no.nav.foreldrepenger.common.domain;

import static no.nav.foreldrepenger.common.oppslag.dkif.Målform.standard;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Søker {

    @NotNull(message = "Søknadsrolle må være satt")
    private final BrukerRolle søknadsRolle;
    private final Målform målform;

    @JsonCreator
    public Søker(@JsonProperty("søknadsRolle") BrukerRolle søknadsRolle,
            @JsonProperty("språkkode") Målform målform) {
        this.søknadsRolle = søknadsRolle;
        this.målform = Optional.ofNullable(målform).orElse(standard());
    }
}

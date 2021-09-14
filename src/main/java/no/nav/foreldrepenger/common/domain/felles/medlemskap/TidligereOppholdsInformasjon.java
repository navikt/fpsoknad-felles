package no.nav.foreldrepenger.common.domain.felles.medlemskap;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.validation.annotations.Opphold;

@Data
@ToString(exclude = "arbeidSiste12")
@EqualsAndHashCode(exclude = "arbeidSiste12")
public class TidligereOppholdsInformasjon {

    private final ArbeidsInformasjon arbeidSiste12;
    @Opphold(fortid = true)
    @Valid
    private final List<Utenlandsopphold> utenlandsOpphold;

    @JsonCreator
    public TidligereOppholdsInformasjon(
            @JsonProperty("arbeidSiste12") ArbeidsInformasjon arbeidSiste12,
            @JsonProperty("utenlandsOpphold") List<Utenlandsopphold> utenlandsOpphold) {
        this.arbeidSiste12 = arbeidSiste12;
        this.utenlandsOpphold = Optional.ofNullable(utenlandsOpphold).orElse(emptyList());
    }

    @JsonIgnore
    public boolean isBoddINorge() {
        return utenlandsOpphold.isEmpty();
    }
}

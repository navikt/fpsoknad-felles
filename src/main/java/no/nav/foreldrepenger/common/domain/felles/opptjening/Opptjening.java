package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Opptjening {

    @Valid
    private final List<UtenlandskArbeidsforhold> utenlandskArbeidsforhold;
    @Valid
    private final List<EgenNæring> egenNæring;
    @Valid
    private final List<AnnenOpptjening> annenOpptjening;
    @Valid
    private final Frilans frilans;

    @JsonCreator
    public Opptjening(@JsonProperty("arbeidsforhold") List<UtenlandskArbeidsforhold> utenlandskArbeidsforhold,
            @JsonProperty("egenNæring") List<EgenNæring> egenNæring,
            @JsonProperty("annenOpptjening") List<AnnenOpptjening> annenOpptjening,
            @JsonProperty("frilans") Frilans frilans) {
        this.utenlandskArbeidsforhold = Optional.ofNullable(utenlandskArbeidsforhold).orElse(emptyList());
        this.egenNæring = Optional.ofNullable(egenNæring).orElse(emptyList());
        this.annenOpptjening = Optional.ofNullable(annenOpptjening).orElse(emptyList());
        this.frilans = frilans;
    }
}

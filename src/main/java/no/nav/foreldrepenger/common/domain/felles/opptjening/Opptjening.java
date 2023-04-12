package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.Valid;

public record Opptjening(@Valid List<UtenlandskArbeidsforhold> utenlandskArbeidsforhold,
                         @Valid List<EgenNæring> egenNæring,
                         @Valid List<AnnenOpptjening> annenOpptjening,
                         @Valid Frilans frilans) {

    @JsonCreator
    public Opptjening {
        utenlandskArbeidsforhold = Optional.ofNullable(utenlandskArbeidsforhold).orElse(emptyList());
        egenNæring = Optional.ofNullable(egenNæring).orElse(emptyList());
        annenOpptjening = Optional.ofNullable(annenOpptjening).orElse(emptyList());
    }
}

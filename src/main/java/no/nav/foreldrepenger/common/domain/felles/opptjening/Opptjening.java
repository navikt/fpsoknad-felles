package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Opptjening(@Valid @Size(max = 20) List<@Valid @NotNull UtenlandskArbeidsforhold> utenlandskArbeidsforhold,
                         @Valid @Size(max = 20) List<@Valid @NotNull EgenNæring> egenNæring,
                         @Valid @Size(max = 20) List<@Valid @NotNull AnnenOpptjening> annenOpptjening,
                         @Valid Frilans frilans) {

    @JsonCreator
    public Opptjening {
        utenlandskArbeidsforhold = Optional.ofNullable(utenlandskArbeidsforhold).orElse(emptyList());
        egenNæring = Optional.ofNullable(egenNæring).orElse(emptyList());
        annenOpptjening = Optional.ofNullable(annenOpptjening).orElse(emptyList());
    }
}

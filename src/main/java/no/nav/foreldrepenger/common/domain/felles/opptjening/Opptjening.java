package no.nav.foreldrepenger.common.domain.felles.opptjening;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;

public record Opptjening(@Valid @JsonAlias("arbeidsforhold") List<UtenlandskArbeidsforhold> utenlandskArbeidsforhold, // TODO: Fjern alias etter expand contract done
                         @Valid List<EgenNæring> egenNæring,
                         @Valid List<AnnenOpptjening> annenOpptjening,
                         @Valid Frilans frilans) {

    @Builder
    @JsonCreator
    public Opptjening {
        utenlandskArbeidsforhold = Optional.ofNullable(utenlandskArbeidsforhold).orElse(emptyList());
        egenNæring = Optional.ofNullable(egenNæring).orElse(emptyList());
        annenOpptjening = Optional.ofNullable(annenOpptjening).orElse(emptyList());
    }
}

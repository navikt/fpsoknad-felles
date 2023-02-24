package no.nav.foreldrepenger.common.domain.felles.opptjening;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

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

//    private Opptjening(Builder b) {
//        this(b.utenlandskArbeidsforhold, b.egenNæring, b.annenOpptjening, b.frilans);
//    }
//
//    public static class Builder {
//        private List<UtenlandskArbeidsforhold> utenlandskArbeidsforhold;
//        private List<EgenNæring> egenNæring;
//        private List<AnnenOpptjening> annenOpptjening;
//        private Frilans frilans;
//
//
//        public Builder() {
//        }
//
//        public Builder utenlandskArbeidsforhold(List<UtenlandskArbeidsforhold> utenlandskArbeidsforhold) {
//            this.utenlandskArbeidsforhold = utenlandskArbeidsforhold;
//            return this;
//        }
//
//        public Builder egenNæring(List<EgenNæring> egenNæring) {
//            this.egenNæring = egenNæring;
//            return this;
//        }
//
//        public Builder annenOpptjening(List<AnnenOpptjening> annenOpptjening) {
//            this.annenOpptjening = annenOpptjening;
//            return this;
//        }
//
//        public Builder frilans(Frilans frilans) {
//            this.frilans = frilans;
//            return this;
//        }
//
//        public Opptjening build() {
//            return new Opptjening(this);
//        }
//    }

}

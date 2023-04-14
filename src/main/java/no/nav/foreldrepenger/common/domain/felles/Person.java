package no.nav.foreldrepenger.common.domain.felles;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Barn;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Person(@Valid AktørId aktørId,
                     @Valid Fødselsnummer fnr,
                     LocalDate fødselsdato,
                     @Valid Navn navn,
                     @Valid Kjønn kjønn,
                     @Valid Målform målform,
                     @Valid Bankkonto bankkonto,
                     @Valid List<Barn> barn,
                     Sivilstand sivilstand) {

    @JsonCreator
    public Person {
        målform = Optional.ofNullable(målform).orElse(Målform.standard());
        barn = Optional.ofNullable(barn).orElse(List.of());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private AktørId aktørId;
        private Fødselsnummer fnr;
        private LocalDate fødselsdato;
        private Navn navn;
        private Kjønn kjønn;
        private Målform målform;
        private Bankkonto bankkonto;
        private List<Barn> barn;
        private Sivilstand sivilstand;

        Builder() {
        }

        public Builder aktørId(AktørId aktørId) {
            this.aktørId = aktørId;
            return this;
        }

        public Builder fnr(Fødselsnummer fnr) {
            this.fnr = fnr;
            return this;
        }

        public Builder fødselsdato(LocalDate fødselsdato) {
            this.fødselsdato = fødselsdato;
            return this;
        }

        public Builder navn(Navn navn) {
            this.navn = navn;
            return this;
        }

        public Builder kjønn(Kjønn kjønn) {
            this.kjønn = kjønn;
            return this;
        }

        public Builder målform(Målform målform) {
            this.målform = målform;
            return this;
        }

        public Builder bankkonto(Bankkonto bankkonto) {
            this.bankkonto = bankkonto;
            return this;
        }

        public Builder barn(List<Barn> barn) {
            this.barn = barn;
            return this;
        }

        public Builder sivilstand(Sivilstand sivilstand) {
            this.sivilstand = sivilstand;
            return this;
        }

        public Person build() {
            return new Person(this.aktørId, this.fnr, this.fødselsdato, this.navn, this.kjønn, this.målform, this.bankkonto, this.barn,
                    this.sivilstand);
        }
    }

}

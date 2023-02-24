package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Barn;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Person(@Valid AktørId aktørId,
                     @Valid Fødselsnummer fnr,
                     LocalDate fødselsdato,
                     @Valid Navn navn,
                     @Valid Kjønn kjønn,
                     @Valid Målform målform,
                     CountryCode land,
                     @Valid Bankkonto bankkonto,
                     @Valid List<Barn> barn) {

    @JsonCreator
    public Person {
        målform = Optional.ofNullable(målform).orElse(Målform.standard());
        land = Optional.ofNullable(land).orElse(CountryCode.NO);
        barn = Optional.ofNullable(barn).orElse(List.of());
    }

    @JsonIgnore
    public String getFornavn() {
        return Optional.ofNullable(navn)
                .map(Navn::fornavn)
                .orElse(null);
    }

    @JsonIgnore
    public String getMellomnavn() {
        return Optional.ofNullable(navn)
                .map(Navn::mellomnavn)
                .orElse(null);
    }

    @JsonIgnore
    public String getEtternavn() {
        return Optional.ofNullable(navn)
                .map(Navn::etternavn)
                .orElse(null);
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
        private CountryCode land;
        private Bankkonto bankkonto;
        private List<Barn> barn;

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

        public Builder land(CountryCode land) {
            this.land = land;
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

        public Person build() {
            return new Person(this.aktørId, this.fnr, this.fødselsdato, this.navn, this.kjønn, this.målform, this.land, this.bankkonto, this.barn);
        }
    }

}

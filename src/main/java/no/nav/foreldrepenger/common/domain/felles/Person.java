package no.nav.foreldrepenger.common.domain.felles;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.emptySet;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;

import lombok.Builder;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Barn;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Person(AktørId aktørId,
                     Fødselsnummer fnr,
                     LocalDate fødselsdato,
                     Navn navn,
                     Kjønn kjønn,
                     Målform målform,
                     CountryCode land,
                     Bankkonto bankkonto,
                     Set<Barn> barn) {

    @Builder
    @JsonCreator
    public Person(AktørId aktørId, Fødselsnummer fnr, LocalDate fødselsdato, Navn navn, Kjønn kjønn, Målform målform,
                  CountryCode land, Bankkonto bankkonto, Set<Barn> barn) {
        this.aktørId = aktørId;
        this.fnr = fnr;
        this.fødselsdato = fødselsdato;
        this.navn = navn;
        this.kjønn = kjønn;
        this.målform = Optional.ofNullable(målform).orElse(Målform.standard());
        this.land = Optional.ofNullable(land).orElse(CountryCode.NO);
        this.bankkonto = bankkonto;
        this.barn = Optional.ofNullable(barn).orElse(emptySet());
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
}

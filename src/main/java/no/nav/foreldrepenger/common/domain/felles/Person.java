package no.nav.foreldrepenger.common.domain.felles;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
public record Person(@Valid AktørId aktørId,
                     @Valid Fødselsnummer fnr,
                     LocalDate fødselsdato,
                     @Valid Navn navn,
                     @Valid Kjønn kjønn,
                     @Valid Målform målform,
                     CountryCode land,
                     @Valid Bankkonto bankkonto,
                     @Valid List<Barn> barn) {
    @Builder
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
}

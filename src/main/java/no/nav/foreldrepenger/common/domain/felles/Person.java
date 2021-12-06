package no.nav.foreldrepenger.common.domain.felles;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.Collections.emptySet;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;
import no.nav.foreldrepenger.common.domain.AktørId;
import no.nav.foreldrepenger.common.domain.Fødselsnummer;
import no.nav.foreldrepenger.common.domain.Navn;
import no.nav.foreldrepenger.common.oppslag.dkif.Målform;
import no.nav.foreldrepenger.common.oppslag.pdl.dto.BarnDTO;

@Data
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    private final Fødselsnummer fnr;
    private final Navn navn;
    private final LocalDate fødselsdato;
    @Exclude
    private final String målform;
    private final CountryCode land;
    private final Bankkonto bankkonto;
    private AktørId aktørId;
    private final Set<BarnDTO> barn;

    @JsonCreator
    public Person(@JsonProperty("fnr") Fødselsnummer fnr,
                  @JsonProperty("navn") Navn navn,
                  @JsonProperty("fødselsdato") LocalDate fødselsdato,
                  @JsonProperty("målform") String målform,
                  @JsonProperty("land") CountryCode land,
                  @JsonProperty("bankkonto") Bankkonto bankkonto, Set<BarnDTO> barn) {
        this.fnr = fnr;
        this.navn = navn;
        this.fødselsdato = fødselsdato;
        this.målform = Optional.ofNullable(målform).orElse(Målform.standard().name());
        this.land = Optional.ofNullable(land).orElse(CountryCode.NO);
        this.bankkonto = bankkonto;
        this.barn = Optional.ofNullable(barn).orElse(emptySet());
    }

    public String getFornavn() {
        return Optional.ofNullable(navn)
                .map(Navn::getFornavn)
                .orElse(null);
    }

    public String getMellomnavn() {
        return Optional.ofNullable(navn)
                .map(Navn::getMellomnavn)
                .orElse(null);
    }

    public String getEtternavn() {
        return Optional.ofNullable(navn)
                .map(Navn::getEtternavn)
                .orElse(null);
    }

    public Kjønn getKjønn() {
        return Optional.ofNullable(navn)
                .map(Navn::getKjønn)
                .orElse(Kjønn.U);

    }
}

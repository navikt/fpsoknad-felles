package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import no.nav.foreldrepenger.common.domain.validation.annotations.BarnOgFødselsdatoer;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@BarnOgFødselsdatoer
public class Fødsel extends RelasjonTilBarn {

    List<@PastOrToday(message = "{ytelse.relasjontilbarn.fødsel.fødselsdato}") LocalDate> fødselsdato;
    LocalDate termindato;

    public Fødsel(LocalDate fødselsdato) {
        this(fødselsdato, null);
    }

    public Fødsel(int antallBarn, LocalDate foedselsdato) {
        this(antallBarn, foedselsdato, null);
    }

    public Fødsel(LocalDate fødselsdato, LocalDate termindato) {
        this(1, fødselsdato, termindato);
    }

    public Fødsel(int antallBarn, LocalDate fødselsDato, LocalDate termindato) {
        this(antallBarn, singletonList(fødselsDato), termindato, emptyList());
    }

    @Builder
    @JsonCreator
    public Fødsel(@JsonProperty("antallBarn") int antallBarn,
                  @JsonProperty("fødselsdato") List<LocalDate> fødselsdato,
                  LocalDate termindato,
                  List<String> vedlegg) {
        super(antallBarn, vedlegg);
        this.fødselsdato = fødselsdato;
        this.termindato = termindato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return fødselsdato.get(0);
    }
}

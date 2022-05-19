package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.validation.annotations.BarnOgFødselsdatoer;
import no.nav.foreldrepenger.common.domain.validation.annotations.PastOrToday;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@BarnOgFødselsdatoer
public final class Fødsel extends RelasjonTilBarn {

    private final List<@PastOrToday(message = "{ytelse.relasjontilbarn.fødsel.fødselsdato}") LocalDate> fødselsdato;
    private final LocalDate termindato;

    @Builder
    @JsonCreator
    public Fødsel(int antallBarn, List<LocalDate> fødselsdato, LocalDate termindato, List<String> vedlegg) {
        super(antallBarn, vedlegg);
        this.fødselsdato = fødselsdato;
        this.termindato = termindato;
    }

    @Override
    public LocalDate relasjonsDato() {
        return fødselsdato.get(0);
    }
}

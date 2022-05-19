package no.nav.foreldrepenger.common.domain.felles.relasjontilbarn;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = Fødsel.class, name = "fødsel"),
        @Type(value = Adopsjon.class, name = "adopsjon"),
        @Type(value = FremtidigFødsel.class, name = "termin"),
        @Type(value = Omsorgsovertakelse.class, name = "omsorgsovertakelse")
})
public abstract sealed class RelasjonTilBarn permits Fødsel,FremtidigFødsel,Adopsjon,Omsorgsovertakelse {

    public abstract LocalDate relasjonsDato();

    private final List<@Pattern(regexp = FRITEKST)String> vedlegg;
    @Positive
    private final int antallBarn;

    protected RelasjonTilBarn(int antallBarn, List<String> vedlegg) {
        this.antallBarn = antallBarn;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}

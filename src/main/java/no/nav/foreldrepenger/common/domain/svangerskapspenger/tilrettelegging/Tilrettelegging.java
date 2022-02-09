package no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Collections.emptyList;
import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

@Data
@EqualsAndHashCode(exclude = { "vedlegg" })
@ToString(exclude = { "vedlegg" })
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HelTilrettelegging.class, name = "hel"),
        @JsonSubTypes.Type(value = DelvisTilrettelegging.class, name = "delvis"),
        @JsonSubTypes.Type(value = IngenTilrettelegging.class, name = "ingen")
})
public abstract class Tilrettelegging {

    @Valid
    private final Arbeidsforhold arbeidsforhold;
    @NotNull
    private final LocalDate behovForTilretteleggingFom;
    private final List<@Pattern(regexp = FRITEKST) String> vedlegg;

    public Tilrettelegging(Arbeidsforhold arbeidsforhold, LocalDate behovForTilretteleggingFom, List<String> vedlegg) {
        this.arbeidsforhold = arbeidsforhold;
        this.behovForTilretteleggingFom = behovForTilretteleggingFom;
        this.vedlegg = Optional.ofNullable(vedlegg).orElse(emptyList());
    }
}

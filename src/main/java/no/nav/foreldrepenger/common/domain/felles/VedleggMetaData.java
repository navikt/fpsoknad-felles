package no.nav.foreldrepenger.common.domain.felles;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import no.nav.foreldrepenger.common.domain.svangerskapspenger.tilrettelegging.arbeidsforhold.Arbeidsforhold;

public record VedleggMetaData(@NotNull UUID uuid,
                              InnsendingsType innsendingsType,
                              @NotNull DokumentType dokumentType,
                              @Pattern(regexp = FRITEKST) String filnavn,
                              @Valid Dokumenterer hvaDokumentererVedlegg,
                              @Length(max = 2000) @Pattern(regexp = FRITEKST) String beskrivelse) {

    public VedleggMetaData(UUID id, InnsendingsType innsendingsType, DokumentType dokumentType) {
        this(id, innsendingsType, dokumentType, null, null, dokumentType.getBeskrivelse());
    }

    @JsonCreator
    public VedleggMetaData {
        beskrivelse = Optional.ofNullable(beskrivelse)
                .orElse(Optional.ofNullable(dokumentType)
                        .map(DokumentType::getBeskrivelse)
                        .orElse(null));
    }

    public record Dokumenterer(@NotNull Type type,
                               @Valid Arbeidsforhold arbeidsforhold,
                               List<@Valid @NotNull LukketPeriode> perioder) {
        public enum Type {
            UTTAK,
            TILRETTELEGGING,
            ANNET
        }
    }
}

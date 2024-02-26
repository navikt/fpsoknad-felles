package no.nav.foreldrepenger.common.domain.felles;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public record VedleggMetaData(@Valid VedleggReferanse id,
                              InnsendingsType innsendingsType,
                              DokumentType dokumentType,
                              @Length(max = 2000) @Pattern(regexp = FRITEKST) String beskrivelse) {

    public VedleggMetaData(VedleggReferanse id, InnsendingsType innsendingsType, DokumentType dokumentType) {
        this(id, innsendingsType, dokumentType, dokumentType.getTittel());
    }

    @JsonCreator
    public VedleggMetaData {
        beskrivelse = Optional.ofNullable(beskrivelse)
                .orElse(Optional.ofNullable(dokumentType)
                        .map(DokumentType::getTittel)
                        .orElse(null));
    }
}

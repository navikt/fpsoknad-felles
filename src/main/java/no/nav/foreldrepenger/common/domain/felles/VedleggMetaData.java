package no.nav.foreldrepenger.common.domain.felles;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

import java.util.Optional;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;

public record VedleggMetaData(@Length(max = 2000) @Pattern(regexp = FRITEKST) String beskrivelse,
                              @Pattern(regexp = FRITEKST) String id,
                              InnsendingsType innsendingsType,
                              DokumentType dokumentType) {

    public VedleggMetaData(String id, InnsendingsType innsendingsType, DokumentType dokumentType) {
        this(dokumentType.getBeskrivelse(), id, innsendingsType, dokumentType);
    }

    @JsonCreator
    public VedleggMetaData {
        beskrivelse = Optional.ofNullable(beskrivelse)
                .orElse(Optional.ofNullable(dokumentType)
                        .map(DokumentType::getBeskrivelse)
                        .orElse(null));
    }
}

package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Optional;

import static no.nav.foreldrepenger.common.domain.validation.InputValideringRegex.FRITEKST;

public record VedleggMetaData(@Valid VedleggReferanse id,
                              InnsendingsType innsendingsType,
                              DokumentType dokumentType,
                              @Length(max = 2000) @Pattern(regexp = FRITEKST) String beskrivelse) {

    public VedleggMetaData(VedleggReferanse id, InnsendingsType innsendingsType, DokumentType dokumentType) {
        this(id, innsendingsType, dokumentType, dokumentType.getBeskrivelse());
    }

    @JsonCreator
    public VedleggMetaData {
        beskrivelse = Optional.ofNullable(beskrivelse)
                .orElse(Optional.ofNullable(dokumentType)
                        .map(DokumentType::getBeskrivelse)
                        .orElse(null));
    }
}

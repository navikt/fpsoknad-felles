package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PåkrevdVedlegg extends Vedlegg {

    @JsonCreator
    public PåkrevdVedlegg(VedleggMetaData metadata, byte[] vedlegg) {
        super(metadata, vedlegg);
    }
}

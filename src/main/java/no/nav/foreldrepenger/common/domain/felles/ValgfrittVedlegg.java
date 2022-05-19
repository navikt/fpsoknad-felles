package no.nav.foreldrepenger.common.domain.felles;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValgfrittVedlegg extends Vedlegg {

    @JsonCreator
    public ValgfrittVedlegg(@Valid VedleggMetaData metadata, byte[] vedlegg) {
        super(metadata, vedlegg);
    }
}

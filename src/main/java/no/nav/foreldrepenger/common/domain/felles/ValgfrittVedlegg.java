package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ValgfrittVedlegg extends Vedlegg {

    @JsonCreator
    public ValgfrittVedlegg(VedleggMetaData metadata) {
        super(metadata);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ValgfrittVedlegg;
    }

    @Override
    public int hashCode() {
        return ValgfrittVedlegg.class.hashCode();
    }

    @Override
    public String toString() {
        return "ValgfrittVedlegg{}" + super.toString();
    }
}

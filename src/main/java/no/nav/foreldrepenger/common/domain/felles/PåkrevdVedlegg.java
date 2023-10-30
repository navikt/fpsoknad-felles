package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PåkrevdVedlegg extends Vedlegg {

    @JsonCreator
    public PåkrevdVedlegg(VedleggMetaData metadata) {
        super(metadata);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof PåkrevdVedlegg;
    }

    @Override
    public int hashCode() {
        return PåkrevdVedlegg.class.hashCode();
    }

    @Override
    public String toString() {
        return "PåkrevdVedlegg{}" + super.toString();
    }
}

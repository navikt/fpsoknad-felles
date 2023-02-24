package no.nav.foreldrepenger.common.domain.felles.annenforelder;


public final class UkjentForelder implements AnnenForelder {

    @Override
    public boolean hasId() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UkjentForelder;
    }

    @Override
    public int hashCode() {
        return UkjentForelder.class.hashCode();
    }
}

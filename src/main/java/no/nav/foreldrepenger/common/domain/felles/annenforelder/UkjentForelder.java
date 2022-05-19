package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class UkjentForelder implements AnnenForelder {

    @Override
    public boolean hasId() {
        return false;
    }
}

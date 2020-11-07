package no.nav.foreldrepenger.common.domain;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum Målform {
    NN,
    @JsonEnumDefaultValue
    NB,
    EN;

    public static Målform defaultSpråk() {
        return NB;
    }
}

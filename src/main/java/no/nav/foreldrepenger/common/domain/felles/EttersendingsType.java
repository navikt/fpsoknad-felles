package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum EttersendingsType {
    @JsonAlias("engangsstønad")
    ENGANGSSTØNAD,
    @JsonAlias("foreldrepenger")
    FORELDREPENGER,
    @JsonAlias("svangerskapspenger")
    SVANGERSKAPSPENGER,
}

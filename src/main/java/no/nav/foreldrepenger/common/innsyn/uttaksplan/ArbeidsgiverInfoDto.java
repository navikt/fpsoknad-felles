package no.nav.foreldrepenger.common.innsyn.uttaksplan;

import static no.nav.foreldrepenger.common.util.StringUtil.mask;

public record ArbeidsgiverInfoDto(String id, String navn, ArbeidsgiverType type) {

    @Override
    public String toString() {
        return "ArbeidsgiverInfoDto{" + "id='" + mask(id) + '\'' + ", navn='" + navn + '\'' + ", type=" + type + '}';
    }
}

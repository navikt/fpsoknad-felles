package no.nav.foreldrepenger.common.domain.felles;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import no.nav.foreldrepenger.common.domain.validation.annotations.Prosent;

public record ProsentAndel(@Prosent @JsonValue Double prosent){

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ProsentAndel valueOf(Number prosent) {
        return new ProsentAndel(prosent.doubleValue());
    }

    @Override
    public Double prosent() { // NOSONAR: Her overrider vi default getter fra record fordi den propagerer annoteringer fra field. Vi ønsker ikke @JsonValue på getter.
        return prosent;
    }
}

package no.nav.foreldrepenger.common.dto.felles;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import no.nav.foreldrepenger.common.dto.serialization.ProsentAndelDeserializer;
import no.nav.foreldrepenger.common.dto.validation.annotations.Prosent;

@Data
@JsonDeserialize(using = ProsentAndelDeserializer.class)
public class ProsentAndel {

    @Prosent
    @JsonValue
    private final Double prosent;

    public ProsentAndel( Number prosent) {
        this.prosent = prosent.doubleValue();
    }
}

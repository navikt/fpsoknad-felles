package no.nav.foreldrepenger.common.dto.serialization;

import static no.nav.foreldrepenger.common.domain.serialization.JacksonUtils.textValue;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.neovisionaries.i18n.CountryCode;

import no.nav.foreldrepenger.common.dto.felles.annenforelder.UtenlandskForelder;

public class UtenlandskForelderDeserializer extends StdDeserializer<UtenlandskForelder> {

    public UtenlandskForelderDeserializer() {
        this(null);
    }

    public UtenlandskForelderDeserializer(Class<UtenlandskForelder> forelder) {
        super(forelder);
    }

    @Override
    public UtenlandskForelder deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode rootNode = p.getCodec().readTree(p);
        return new UtenlandskForelder(textValue(rootNode, "id"),
                CountryCode.valueOf(textValue(rootNode, "land")), textValue(rootNode, "navn"));
    }

}

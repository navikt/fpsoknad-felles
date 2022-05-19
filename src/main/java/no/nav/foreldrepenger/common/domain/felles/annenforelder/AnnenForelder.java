package no.nav.foreldrepenger.common.domain.felles.annenforelder;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = UkjentForelder.class, name = "ukjent"),
        @Type(value = UtenlandskForelder.class, name = "utenlandsk"),
        @Type(value = NorskForelder.class, name = "norsk")
})
public interface AnnenForelder {

    boolean hasId();
}

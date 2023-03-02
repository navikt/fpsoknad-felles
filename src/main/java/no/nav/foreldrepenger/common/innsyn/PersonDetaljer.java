package no.nav.foreldrepenger.common.innsyn;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import no.nav.foreldrepenger.common.innsyn.persondetaljer.AktørId;
import no.nav.foreldrepenger.common.innsyn.persondetaljer.Person;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AktørId.class, name = "aktørId"),
    @JsonSubTypes.Type(value = Person.class, name = "person")
})
public interface PersonDetaljer {
}

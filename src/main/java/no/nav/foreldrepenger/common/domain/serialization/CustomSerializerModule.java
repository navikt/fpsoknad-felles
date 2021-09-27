package no.nav.foreldrepenger.common.domain.serialization;

import static com.fasterxml.jackson.core.json.PackageVersion.VERSION;

import com.fasterxml.jackson.databind.module.SimpleModule;

import no.nav.foreldrepenger.common.domain.Orgnummer;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.NorskForelder;
import no.nav.foreldrepenger.common.domain.felles.annenforelder.UtenlandskForelder;
import no.nav.foreldrepenger.common.domain.felles.medlemskap.Medlemsskap;

public class CustomSerializerModule extends SimpleModule {
    public CustomSerializerModule() {
        super(VERSION);
        addSerializers();
        addDeserializers();
    }

    private void addDeserializers() {
        addDeserializer(Medlemsskap.class, new MedlemsskapDeserializer());
        addDeserializer(UtenlandskForelder.class, new UtenlandskForelderDeserializer());
        addDeserializer(NorskForelder.class, new NorskForelderDeserializer());
        addDeserializer(Orgnummer.class, new OrgnummerDeserializer());
    }

    private void addSerializers() {
        addSerializer(UtenlandskForelder.class, new UtenlandskForelderSerializer());
        addSerializer(Medlemsskap.class, new MedlemsskapSerializer());
        addSerializer(NorskForelder.class, new NorskForelderSerializer());
    }
}

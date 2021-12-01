package no.nav.foreldrepenger.common.dto.serialization;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return Optional.ofNullable(v)
                .map(LocalDateTime::toString)
                .orElse(null);
    }
}

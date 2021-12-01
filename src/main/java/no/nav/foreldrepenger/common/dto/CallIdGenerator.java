package no.nav.foreldrepenger.common.dto;

import java.util.UUID;

public class CallIdGenerator {

    public String create() {
        return UUID.randomUUID().toString();
    }
}

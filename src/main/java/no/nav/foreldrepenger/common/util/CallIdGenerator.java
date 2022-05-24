package no.nav.foreldrepenger.common.util;

import java.util.UUID;

public class CallIdGenerator {

    public String create() {
        return UUID.randomUUID().toString();
    }
}

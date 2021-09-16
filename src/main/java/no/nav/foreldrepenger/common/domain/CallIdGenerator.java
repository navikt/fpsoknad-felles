package no.nav.foreldrepenger.common.domain;

import java.util.UUID;

//@Component TODO spring boot
public class CallIdGenerator {

    public String create() {
        return UUID.randomUUID().toString();
    }
}

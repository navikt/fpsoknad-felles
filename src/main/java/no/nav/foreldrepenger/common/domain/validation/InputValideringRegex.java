package no.nav.foreldrepenger.common.domain.validation;

public class InputValideringRegex {

    public static final String FRITEKST = "^[0-9a-zA-ZÁáĄąÂâĀāĂăßČčĆćÇçĎďĐđÐðĔĕÉéĘęĖėÈèËëÊêĒēĢģİiĮįÍíÎîÏïĪīĶķŁłŊŋŇňŃńŅņÑñÞþŠšŚśŞşŤťŦŧŢţŲųŪūÚúŮůÝýŽžŹźŻżÕõÔôÓóÖöÜüÄäŒœÆæØøÅå .\…'\\-/\n%§\\!?@_()+:;,=\"&]*$";

    private InputValideringRegex() {
        throw new IllegalAccessError("Skal ikke instansieres");
    }
}

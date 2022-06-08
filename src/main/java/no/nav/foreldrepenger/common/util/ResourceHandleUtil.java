package no.nav.foreldrepenger.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class ResourceHandleUtil {

    private ResourceHandleUtil() {

    }

    public static String copyToString(String filnavn) {
        try (var br = new BufferedReader(new InputStreamReader(getInputStreamFromResource(filnavn)))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] bytesFra(String filnavn) {
        try (InputStream is = getInputStreamFromResource(filnavn)) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static InputStream getInputStreamFromResource(String fileName) {
        var inputStream = ResourceHandleUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    public static URL getURLFromResource(String fileName) {
        var url = ResourceHandleUtil.class.getClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return url;
        }
    }
}

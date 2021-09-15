package no.nav.foreldrepenger.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.cxf.helpers.IOUtils;

public class ResourceHandleUtil {

    private ResourceHandleUtil() {

    }

    public static String copyToString(String filnavn) {
        try (InputStream is = getInputStreamFromResource(filnavn)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] bytesFra(String filnavn) {
        try (InputStream is = getInputStreamFromResource(filnavn)) {
            return IOUtils.readBytesFromStream(is);
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

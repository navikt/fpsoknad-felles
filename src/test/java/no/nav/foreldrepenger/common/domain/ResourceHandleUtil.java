package no.nav.foreldrepenger.common.domain;

import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;

public class ResourceHandleUtil {

    private ResourceHandleUtil() {

    }

    public static String copyToString(String filnavn) {
        try (InputStream is = getFileFromResourceAsStream(filnavn)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] bytesFra(String filnavn) {
        try (InputStream is = getFileFromResourceAsStream(filnavn)) {
            return IOUtils.readBytesFromStream(is);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {
        var inputStream = ResourceHandleUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}

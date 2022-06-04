package no.nav.foreldrepenger.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ResourceHandleUtil {

    private ResourceHandleUtil() {

    }

    public static String copyToString(String filnavn) {
        try (InputStream is = getInputStreamFromResource(filnavn)) {
            return readFromInputStream(is);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static byte[] bytesFra(String filnavn) {
        try {
            return Files.readAllBytes(getPath(filnavn));
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

    private static Path getPath(String fileName) {
        return Path.of(Objects.requireNonNull(ResourceHandleUtil.class.getClassLoader().getResource(fileName)).getPath());
    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}

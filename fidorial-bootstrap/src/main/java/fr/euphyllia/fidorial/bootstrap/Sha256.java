package fr.euphyllia.fidorial.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

final class Sha256 {

    private Sha256() {
    }

    static String of(final Path file) throws IOException {
        final MessageDigest digest = newDigest();
        try (final InputStream in = new DigestInputStream(Files.newInputStream(file), digest)) {
            in.transferTo(OutputStream.nullOutputStream());
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    static String of(final byte[] bytes) {
        return HexFormat.of().formatHex(newDigest().digest(bytes));
    }

    private static MessageDigest newDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is required by the JLS but missing", e);
        }
    }
}

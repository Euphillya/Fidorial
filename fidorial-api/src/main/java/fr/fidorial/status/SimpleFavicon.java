package fr.fidorial.status;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

record SimpleFavicon(byte[] data) implements Favicon {
    public static Favicon read(final Path path) throws IOException {
        try (final InputStream stream = Files.newInputStream(path)) {
            return read(stream);
        }
    }

    public static Favicon read(final InputStream stream) throws IOException {
        final BufferedImage image = ImageIO.read(stream);
        if (image == null) {
            throw new IllegalArgumentException("Unsupported favicon image format");
        }
        return of(image);
    }

    public static Favicon of(final BufferedImage image) throws IOException {
        if (image.getWidth() != 64 || image.getHeight() != 64) {
            throw new IllegalArgumentException("Server favicon must be 64x64 pixels");
        }

        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (!ImageIO.write(image, "png", output)) {
            throw new IllegalStateException("No PNG image writer is available");
        }
        return new SimpleFavicon(output.toByteArray());
    }
}

package fr.fidorial.status;

import org.jetbrains.annotations.Contract;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * A favicon encoded as PNG image data.
 * <p>
 * Status responses require favicons to be 64 by 64 pixels.
 * Factory methods validate the source image and encode it as PNG data.
 *
 * @since 0.1.0
 */
public sealed interface Favicon permits SimpleFavicon {
    /**
     * Gets the PNG-encoded favicon image data.
     *
     * @return favicon data
     * @since 0.1.0
     */
    @Contract(value = " -> new", pure = true)
    byte[] data();

    /**
     * Reads a favicon from an image file.
     *
     * @param path path to the image file
     * @return favicon encoded as PNG data
     * @throws IOException              if the image cannot be read or encoded
     * @throws IllegalArgumentException if the image format is unsupported or the image is not 64 by 64 pixels
     * @since 0.1.0
     */
    @Contract(value = "_ -> new", pure = true)
    static Favicon read(final Path path) throws IOException {
        return SimpleFavicon.read(path);
    }

    /**
     * Reads a favicon from an image stream.
     *
     * @param stream stream containing image data
     * @return favicon encoded as PNG data
     * @throws IOException              if the image cannot be read or encoded
     * @throws IllegalArgumentException if the image format is unsupported or the image is not 64 by 64 pixels
     * @since 0.1.0
     */
    @Contract(value = "_ -> new", pure = true)
    static Favicon read(final InputStream stream) throws IOException {
        return SimpleFavicon.read(stream);
    }

    /**
     * Creates a favicon from an image.
     *
     * @param image source image
     * @return favicon encoded as PNG data
     * @throws IOException              if the image cannot be encoded
     * @throws IllegalArgumentException if the image is not 64 by 64 pixels
     * @since 0.1.0
     */
    @Contract(value = "_ -> new", pure = true)
    static Favicon of(final BufferedImage image) throws IOException {
        return SimpleFavicon.of(image);
    }
}

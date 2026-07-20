package fr.fidorial.status;

import org.jetbrains.annotations.Contract;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public sealed interface Favicon permits SimpleFavicon {
    byte[] data();

    @Contract(value = "_ -> new", pure = true)
    static Favicon read(Path path) throws IOException {
        return SimpleFavicon.read(path);
    }

    @Contract(value = "_ -> new", pure = true)
    static Favicon read(InputStream stream) throws IOException {
        return SimpleFavicon.read(stream);
    }

    @Contract(value = "_ -> new", pure = true)
    static Favicon of(BufferedImage image) throws IOException {
        return SimpleFavicon.of(image);
    }
}

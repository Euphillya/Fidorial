package fr.euphyllia.fidorial.server.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.kyori.adventure.key.Key;

import java.util.UUID;
import java.util.stream.IntStream;

public final class CommonCodecs {

    private CommonCodecs() {
    }

    public static final Codec<UUID> UUID_CODEC = Codec.INT_STREAM.comapFlatMap(
            stream -> {
                final int[] i = stream.toArray();
                if (i.length != 4) {
                    return DataResult.error(() -> "Expected 4 ints for a UUID, got " + i.length);
                }
                return DataResult.success(new UUID(
                        ((long) i[0] << 32) | (i[1] & 0xFFFFFFFFL),
                        ((long) i[2] << 32) | (i[3] & 0xFFFFFFFFL)));
            },
            uuid -> IntStream.of(
                    (int) (uuid.getMostSignificantBits() >> 32),
                    (int) uuid.getMostSignificantBits(),
                    (int) (uuid.getLeastSignificantBits() >> 32),
                    (int) uuid.getLeastSignificantBits())
    );

    public static final Codec<Key> KEY_CODEC = Codec.STRING.comapFlatMap(
            s -> Key.parseable(s)
                    ? DataResult.success(Key.key(s))
                    : DataResult.error(() -> "Not a valid key: " + s),
            Key::asString
    );
}

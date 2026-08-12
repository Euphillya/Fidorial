package fr.euphyllia.fidorial.server.world.nbt.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtByte;
import fr.euphyllia.fidorial.server.world.nbt.NbtByteArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtDouble;
import fr.euphyllia.fidorial.server.world.nbt.NbtEnd;
import fr.euphyllia.fidorial.server.world.nbt.NbtFloat;
import fr.euphyllia.fidorial.server.world.nbt.NbtInt;
import fr.euphyllia.fidorial.server.world.nbt.NbtIntArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtLong;
import fr.euphyllia.fidorial.server.world.nbt.NbtLongArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtShort;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class NbtOps implements DynamicOps<Nbt> {

    public static final NbtOps INSTANCE = new NbtOps();

    private NbtOps() {
    }

    @Override
    public Nbt empty() {
        return new NbtEnd();
    }

    @Override
    public <U> U convertTo(final DynamicOps<U> outOps, final Nbt input) {
        return switch (input) {
            case NbtByte(byte v) -> outOps.createByte(v);
            case NbtShort(short v) -> outOps.createShort(v);
            case NbtInt(int v) -> outOps.createInt(v);
            case NbtLong(long v) -> outOps.createLong(v);
            case NbtFloat(float v) -> outOps.createFloat(v);
            case NbtDouble(double v) -> outOps.createDouble(v);
            case NbtString(String v) -> outOps.createString(v);
            case NbtByteArray(byte[] v) -> outOps.createByteList(ByteBuffer.wrap(v));
            case NbtIntArray(int[] v) -> outOps.createIntList(IntStream.of(v));
            case NbtLongArray(long[] v) -> outOps.createLongList(LongStream.of(v));
            case NbtList list -> convertList(outOps, list);
            case NbtCompound compound -> convertMap(outOps, compound);
            case NbtEnd _ -> outOps.empty();
        };
    }

    @Override
    public DataResult<Number> getNumberValue(final Nbt input) {
        return switch (input) {
            case NbtByte(byte v) -> DataResult.success(v);
            case NbtShort(short v) -> DataResult.success(v);
            case NbtInt(int v) -> DataResult.success(v);
            case NbtLong(long v) -> DataResult.success(v);
            case NbtFloat(float v) -> DataResult.success(v);
            case NbtDouble(double v) -> DataResult.success(v);
            default -> DataResult.error(() -> "Not a number: " + input);
        };
    }

    @Override
    public Nbt createNumeric(final Number i) {
        if (i instanceof Byte b) return new NbtByte(b);
        if (i instanceof Short s) return new NbtShort(s);
        if (i instanceof Integer n) return new NbtInt(n);
        if (i instanceof Long l) return new NbtLong(l);
        if (i instanceof Float f) return new NbtFloat(f);
        return new NbtDouble(i.doubleValue());
    }

    @Override
    public Nbt createByte(final byte value) {
        return new NbtByte(value);
    }

    @Override
    public Nbt createShort(final short value) {
        return new NbtShort(value);
    }

    @Override
    public Nbt createInt(final int value) {
        return new NbtInt(value);
    }

    @Override
    public Nbt createLong(final long value) {
        return new NbtLong(value);
    }

    @Override
    public Nbt createFloat(final float value) {
        return new NbtFloat(value);
    }

    @Override
    public Nbt createDouble(final double value) {
        return new NbtDouble(value);
    }

    @Override
    public DataResult<Boolean> getBooleanValue(final Nbt input) {
        return getNumberValue(input).map(n -> n.byteValue() != 0);
    }

    @Override
    public Nbt createBoolean(final boolean value) {
        return new NbtByte((byte) (value ? 1 : 0));
    }

    @Override
    public DataResult<String> getStringValue(final Nbt input) {
        if (input instanceof NbtString(String v)) {
            return DataResult.success(v);
        }
        return DataResult.error(() -> "Not a string: " + input);
    }

    @Override
    public Nbt createString(final String value) {
        return new NbtString(value);
    }

    @Override
    public DataResult<Stream<Nbt>> getStream(final Nbt input) {
        if (input instanceof NbtList list) {
            return DataResult.success(list.items().stream());
        }
        return DataResult.error(() -> "Not a list: " + input);
    }

    @Override
    public Nbt createList(final Stream<Nbt> input) {
        NbtList list = new NbtList();
        input.forEach(list::add);
        return list;
    }

    @Override
    public DataResult<Nbt> mergeToList(final Nbt list, final Nbt value) {
        if (!(list instanceof NbtEnd) && !(list instanceof NbtList)) {
            return DataResult.error(() -> "mergeToList called with not a list: " + list);
        }
        NbtList result = new NbtList();
        if (list instanceof NbtList existing) {
            for (Nbt item : existing) result.add(item);
        }
        result.add(value);
        return DataResult.success(result);
    }

    @Override
    public DataResult<ByteBuffer> getByteBuffer(final Nbt input) {
        if (input instanceof NbtByteArray(byte[] v)) {
            return DataResult.success(ByteBuffer.wrap(v));
        }
        return DataResult.error(() -> "Not a byte array: " + input);
    }

    @Override
    public Nbt createByteList(final ByteBuffer input) {
        ByteBuffer dup = input.duplicate();
        byte[] arr = new byte[dup.remaining()];
        dup.get(arr);
        return new NbtByteArray(arr);
    }

    @Override
    public DataResult<IntStream> getIntStream(final Nbt input) {
        if (input instanceof NbtIntArray(int[] v)) {
            return DataResult.success(IntStream.of(v));
        }
        return DataResult.error(() -> "Not an int array: " + input);
    }

    @Override
    public Nbt createIntList(final IntStream input) {
        return new NbtIntArray(input.toArray());
    }

    @Override
    public DataResult<LongStream> getLongStream(final Nbt input) {
        if (input instanceof NbtLongArray(long[] v)) {
            return DataResult.success(LongStream.of(v));
        }
        return DataResult.error(() -> "Not a long array: " + input);
    }

    @Override
    public Nbt createLongList(final LongStream input) {
        return new NbtLongArray(input.toArray());
    }

    @Override
    public DataResult<Stream<Pair<Nbt, Nbt>>> getMapValues(final Nbt input) {
        if (!(input instanceof NbtCompound compound)) {
            return DataResult.error(() -> "Not a compound: " + input);
        }
        return DataResult.success(compound.entrySet().stream()
                .map(e -> Pair.of(new NbtString(e.getKey()), e.getValue())));
    }

    @Override
    public Nbt createMap(final Stream<Pair<Nbt, Nbt>> map) {
        NbtCompound compound = new NbtCompound();
        map.forEach(pair -> {
            if (!(pair.getFirst() instanceof NbtString(String key))) {
                throw new IllegalArgumentException("Compound keys must be strings, got: " + pair.getFirst());
            }
            compound.put(key, pair.getSecond());
        });
        return compound;
    }

    @Override
    public DataResult<Nbt> mergeToMap(final Nbt map, final Nbt key, final Nbt value) {
        if (!(map instanceof NbtEnd) && !(map instanceof NbtCompound)) {
            return DataResult.error(() -> "mergeToMap called with not a map: " + map);
        }
        if (!(key instanceof NbtString(String k))) {
            return DataResult.error(() -> "key is not a string: " + key);
        }
        NbtCompound result = new NbtCompound();
        if (map instanceof final NbtCompound existing) {
            result.tags().putAll(existing.tags());
        }
        result.put(k, value);
        return DataResult.success(result);
    }

    @Override
    public Nbt remove(final Nbt input, final String key) {
        if (input instanceof NbtCompound compound) {
            NbtCompound copy = new NbtCompound();
            copy.tags().putAll(compound.tags());
            copy.tags().remove(key);
            return copy;
        }
        return input;
    }
}

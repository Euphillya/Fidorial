package fr.euphyllia.fidorial.server.world.nbt;

import io.netty.handler.codec.DecoderException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class NbtIo {

    private NbtIo() {
    }

    public static void write(DataOutput out, String rootName, NbtCompound root) throws IOException {
        out.writeByte(NbtType.COMPOUND.id());
        out.writeUTF(rootName);
        writePayload(out, root);
    }

    public static byte[] writeToBytes(String rootName, NbtCompound root) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
        try (DataOutputStream out = new DataOutputStream(baos)) {
            write(out, rootName, root);
        }
        return baos.toByteArray();
    }

    public static void writeGzip(Path file, String rootName, NbtCompound root) throws IOException {
        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        try (DataOutputStream out =
                     new DataOutputStream(new GZIPOutputStream(new BufferedOutputStream(Files.newOutputStream(tmp))))) {
            write(out, rootName, root);
        }
        // level.dat_old : sauvegarde comme le fait vanilla
        if (Files.exists(file)) {
            Files.copy(
                    file,
                    file.resolveSibling(file.getFileName() + "_old"),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        Files.move(
                tmp,
                file,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                java.nio.file.StandardCopyOption.ATOMIC_MOVE);
    }

    public static void writePayload(DataOutput out, Nbt tag) throws IOException {
        switch (tag) {
            case NbtByte b -> out.writeByte(b.value());
            case NbtShort s -> out.writeShort(s.value());
            case NbtInt i -> out.writeInt(i.value());
            case NbtLong l -> out.writeLong(l.value());
            case NbtFloat f -> out.writeFloat(f.value());
            case NbtDouble d -> out.writeDouble(d.value());
            case NbtByteArray a -> {
                out.writeInt(a.value().length);
                out.write(a.value());
            }
            case NbtString s -> out.writeUTF(s.value());
            case NbtIntArray a -> {
                out.writeInt(a.value().length);
                for (int v : a.value()) out.writeInt(v);
            }
            case NbtLongArray a -> {
                out.writeInt(a.value().length);
                for (long v : a.value()) out.writeLong(v);
            }
            case NbtList list -> {
                NbtType elementType = list.elementType();

                out.writeByte(elementType.id());
                out.writeInt(list.size());

                for (Nbt item : list.items()) {
                    if (elementType == NbtType.COMPOUND && item.type() != NbtType.COMPOUND) {
                        NbtCompound wrapper = new NbtCompound().put("", item);
                        writePayload(out, wrapper);
                    } else {
                        writePayload(out, item);
                    }
                }
            }
            case NbtCompound compound -> {
                for (var e : compound.tags().entrySet()) {
                    out.writeByte(e.getValue().type().id());
                    out.writeUTF(e.getKey());
                    writePayload(out, e.getValue());
                }
                out.writeByte(NbtType.END.id());
            }
        }
    }

    public static Named read(DataInput in) throws IOException {
        return read(in, NbtReadLimits.noBudget());
    }

    public static Named read(DataInput in, NbtReadLimits limits) throws IOException {
        int rootType = in.readUnsignedByte();
        if (rootType != NbtType.COMPOUND.id()) {
            throw new IOException("Racine NBT attendue TAG_Compound, reçu " + rootType);
        }
        String name = readUtf(in, limits);
        NbtCompound root = (NbtCompound) readPayload(in, NbtType.COMPOUND, limits, 0);
        return new Named(name, root);
    }

    public static Named readFromBytes(byte[] data) throws IOException {
        return read(new DataInputStream(new ByteArrayInputStream(data)));
    }

    public static Named readFromBytes(byte[] data, NbtReadLimits limits) throws IOException {
        return read(new DataInputStream(new ByteArrayInputStream(data)), limits);
    }

    public static Named readGzip(Path file) throws IOException {
        try (DataInputStream in =
                     new DataInputStream(new GZIPInputStream(new BufferedInputStream(Files.newInputStream(file))))) {
            return read(in);
        }
    }

    public static Nbt readPayload(DataInput in, NbtType type, NbtReadLimits limits, int depth) throws IOException {
        return switch (type) {
            case END -> throw new IOException("TAG_End inattendu");
            case BYTE -> { limits.spendFor(type); yield new NbtByte(in.readByte()); }
            case SHORT -> { limits.spendFor(type); yield new NbtShort(in.readShort()); }
            case INT -> { limits.spendFor(type); yield new NbtInt(in.readInt()); }
            case LONG -> { limits.spendFor(type); yield new NbtLong(in.readLong()); }
            case FLOAT -> { limits.spendFor(type); yield new NbtFloat(in.readFloat()); }
            case DOUBLE -> { limits.spendFor(type); yield new NbtDouble(in.readDouble()); }
            case BYTE_ARRAY -> {
                limits.spendFor(type);
                int len = in.readInt();
                if (len < 0) throw new DecoderException("Negative TAG_Byte_Array length: " + len);
                limits.spend(len);
                byte[] arr = new byte[len];
                in.readFully(arr);
                yield new NbtByteArray(arr);
            }
            case STRING -> { limits.spendFor(type); yield new NbtString(readUtf(in, limits)); }
            case INT_ARRAY -> {
                limits.spendFor(type);
                int len = in.readInt();
                if (len < 0) throw new DecoderException("Negative TAG_Int_Array length: " + len);
                limits.spend((long) len * 4L);
                int[] arr = new int[len];
                for (int i = 0; i < len; i++) arr[i] = in.readInt();
                yield new NbtIntArray(arr);
            }
            case LONG_ARRAY -> {
                limits.spendFor(type);
                int len = in.readInt();
                if (len < 0) throw new DecoderException("Negative TAG_Long_Array length: " + len);
                limits.spend((long) len * 8L);
                long[] arr = new long[len];
                for (int i = 0; i < len; i++) arr[i] = in.readLong();
                yield new NbtLongArray(arr);
            }
            case LIST -> {
                limits.spendFor(type);
                limits.checkDepth(depth + 1);
                NbtType elem = NbtType.byId(in.readUnsignedByte());
                int len = in.readInt();
                if (len < 0) throw new DecoderException("Negative TAG_List length: " + len);
                limits.spend((long) len * 4L);

                NbtList list = new NbtList();
                for (int i = 0; i < len; i++) {
                    Nbt item = readPayload(in, elem, limits, depth + 1);
                    if (elem == NbtType.COMPOUND
                            && item instanceof NbtCompound compound
                            && compound.size() == 1
                            && compound.contains("")) {
                        item = compound.get("");
                    }
                    list.add(item);
                }
                yield list;
            }
            case COMPOUND -> {
                limits.spendFor(type);
                limits.checkDepth(depth + 1);
                NbtCompound compound = new NbtCompound();
                while (true) {
                    int id = in.readUnsignedByte();
                    if (id == NbtType.END.id()) break;
                    NbtType childType = NbtType.byId(id);
                    String key = readUtf(in, limits);
                    limits.spendEntry();
                    compound.put(key, readPayload(in, childType, limits, depth + 1));
                }
                yield compound;
            }
        };
    }

    private static String readUtf(DataInput in, NbtReadLimits limits) throws IOException {
        String s = in.readUTF();
        limits.spend(2L + (long) s.length() * 3L);
        return s;
    }

    public static void writeNetwork(DataOutput out, Nbt tag) throws IOException {
        out.writeByte(tag.type().id());
        writePayload(out, tag);
    }

    public static Nbt readNetwork(DataInput in, NbtReadLimits limits) throws IOException {
        return readPayload(in, NbtType.byId(in.readUnsignedByte()), limits, 0);
    }

    public static byte[] writeNetworkToBytes(Nbt tag) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
        try (DataOutputStream out = new DataOutputStream(baos)) {
            writeNetwork(out, tag);
        } catch (IOException e) {
            throw new RuntimeException("Serialisation NBT reseau impossible", e);
        }
        return baos.toByteArray();
    }

    public record Named(String name, NbtCompound compound) {
    }
}

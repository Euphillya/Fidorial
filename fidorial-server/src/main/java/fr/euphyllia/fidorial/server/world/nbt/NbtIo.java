package fr.euphyllia.fidorial.server.world.nbt;

import java.io.*;
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
        try (DataOutputStream out = new DataOutputStream(
                new GZIPOutputStream(new BufferedOutputStream(Files.newOutputStream(tmp))))) {
            write(out, rootName, root);
        }
        // level.dat_old : sauvegarde comme le fait vanilla
        if (Files.exists(file)) {
            Files.copy(file, file.resolveSibling(file.getFileName() + "_old"),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        Files.move(tmp, file,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                java.nio.file.StandardCopyOption.ATOMIC_MOVE);
    }

    private static void writePayload(DataOutput out, Nbt tag) throws IOException {
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
                out.writeByte(list.elementType().id());
                out.writeInt(list.size());
                for (Nbt item : list.items()) {
                    writePayload(out, item);
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
        int rootType = in.readUnsignedByte();
        if (rootType != NbtType.COMPOUND.id()) {
            throw new IOException("Racine NBT attendue TAG_Compound, reçu " + rootType);
        }
        String name = in.readUTF();
        NbtCompound root = (NbtCompound) readPayload(in, NbtType.COMPOUND);
        return new Named(name, root);
    }

    public static Named readFromBytes(byte[] data) throws IOException {
        return read(new DataInputStream(new ByteArrayInputStream(data)));
    }

    public static Named readGzip(Path file) throws IOException {
        try (DataInputStream in = new DataInputStream(
                new GZIPInputStream(new BufferedInputStream(Files.newInputStream(file))))) {
            return read(in);
        }
    }

    private static Nbt readPayload(DataInput in, NbtType type) throws IOException {
        return switch (type) {
            case END -> throw new IOException("TAG_End inattendu");
            case BYTE -> new NbtByte(in.readByte());
            case SHORT -> new NbtShort(in.readShort());
            case INT -> new NbtInt(in.readInt());
            case LONG -> new NbtLong(in.readLong());
            case FLOAT -> new NbtFloat(in.readFloat());
            case DOUBLE -> new NbtDouble(in.readDouble());
            case BYTE_ARRAY -> {
                int len = in.readInt();
                byte[] arr = new byte[len];
                in.readFully(arr);
                yield new NbtByteArray(arr);
            }
            case STRING -> new NbtString(in.readUTF());
            case INT_ARRAY -> {
                int len = in.readInt();
                int[] arr = new int[len];
                for (int i = 0; i < len; i++) arr[i] = in.readInt();
                yield new NbtIntArray(arr);
            }
            case LONG_ARRAY -> {
                int len = in.readInt();
                long[] arr = new long[len];
                for (int i = 0; i < len; i++) arr[i] = in.readLong();
                yield new NbtLongArray(arr);
            }
            case LIST -> {
                NbtType elem = NbtType.byId(in.readUnsignedByte());
                int len = in.readInt();
                NbtList list = new NbtList(len == 0 ? NbtType.END : elem);
                for (int i = 0; i < len; i++) {
                    list.add(readPayload(in, elem));
                }
                yield list;
            }
            case COMPOUND -> {
                NbtCompound compound = new NbtCompound();
                while (true) {
                    int id = in.readUnsignedByte();
                    if (id == NbtType.END.id()) break;
                    NbtType childType = NbtType.byId(id);
                    String key = in.readUTF();
                    compound.put(key, readPayload(in, childType));
                }
                yield compound;
            }
        };
    }

    public static void writeNetwork(DataOutput out, Nbt tag) throws IOException {
        out.writeByte(tag.type().id());
        writePayload(out, tag);
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

package fr.euphyllia.fidorial.server.world.entity;

import fr.euphyllia.fidorial.server.entity.AbstractEntity;
import fr.euphyllia.fidorial.server.entity.EntityTypes;
import fr.euphyllia.fidorial.server.entity.mob.Mob;
import fr.euphyllia.fidorial.server.entity.mob.Mobs;
import fr.euphyllia.fidorial.server.entity.mob.PathfinderMob;
import fr.euphyllia.fidorial.server.world.chunk.AnvilChunkSerializer;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtCompound;
import fr.euphyllia.fidorial.server.world.nbt.NbtDouble;
import fr.euphyllia.fidorial.server.world.nbt.NbtFloat;
import fr.euphyllia.fidorial.server.world.nbt.NbtList;
import fr.euphyllia.fidorial.server.world.nbt.NbtType;
import fr.fidorial.entity.EntityType;
import fr.fidorial.entity.LivingEntity;
import fr.fidorial.world.Location;
import fr.fidorial.world.World;
import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.IntSupplier;

public class AnvilEntitySerializer {

    private final int dataVersion;

    public AnvilEntitySerializer() {
        this(AnvilChunkSerializer.DATA_VERSION_26_2);
    }

    public AnvilEntitySerializer(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public static boolean isPersistable(AbstractEntity entity) {
        return entity instanceof Mob && !entity.isRemoved();
    }

    public NbtCompound toChunkNbt(int chunkX, int chunkZ, Collection<? extends AbstractEntity> entities) {
        NbtCompound root = new NbtCompound();
        root.putInt("DataVersion", dataVersion);
        root.putIntArray("Position", new int[] {chunkX, chunkZ});

        NbtList list = new NbtList(NbtType.COMPOUND);
        for (AbstractEntity entity : entities) {
            if (isPersistable(entity)) {
                list.add(toNbt(entity));
            }
        }
        root.put("Entities", list);
        return root;
    }

    public NbtCompound toNbt(AbstractEntity entity) {
        NbtCompound c = new NbtCompound();
        c.putString("id", entity.type().key().asString());
        c.putIntArray("UUID", uuidToInts(entity.uuid()));

        Location loc = entity.location();
        c.put("Pos", doubleList(loc.x(), loc.y(), loc.z()));

        if (entity instanceof PathfinderMob mob) {
            c.put("Motion", doubleList(mob.velocityX(), mob.velocityY(), mob.velocityZ()));
            c.putBoolean("OnGround", mob.onGround());
        } else {
            c.put("Motion", doubleList(0.0, 0.0, 0.0));
            c.putBoolean("OnGround", false);
        }
        c.put("Rotation", floatList(loc.yaw(), loc.pitch()));

        c.putFloat("FallDistance", 0f);
        c.putShort("Fire", -20);
        c.putShort("Air", 300);

        if (entity instanceof LivingEntity living) {
            c.putFloat("Health", living.health());
        }
        return c;
    }

    public List<AbstractEntity> fromChunkNbt(NbtCompound root, World world, IntSupplier idAllocator) {
        List<AbstractEntity> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        NbtList entities = root.getList("Entities");
        if (entities == null) {
            return result;
        }
        for (Nbt tag : entities.items()) {
            if (tag instanceof NbtCompound entry) {
                AbstractEntity entity = fromNbt(entry, world, idAllocator);
                if (entity != null) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    @SuppressWarnings("PatternValidation")
    public AbstractEntity fromNbt(NbtCompound c, World world, IntSupplier idAllocator) {
        String id = c.getString("id");
        if (id.isEmpty()) {
            return null;
        }
        EntityType type = EntityTypes.get(Key.key(id));
        if (type == null || !Mobs.isMob(type)) {
            return null;
        }

        NbtList pos = c.getList("Pos");
        NbtList rot = c.getList("Rotation");
        double x = doubleAt(pos, 0);
        double y = doubleAt(pos, 1);
        double z = doubleAt(pos, 2);
        float yaw = floatAt(rot, 0);
        float pitch = floatAt(rot, 1);
        Location location = new Location(x, y, z, yaw, pitch);

        Mob mob = Mobs.create(type, idAllocator.getAsInt(), world, location);

        int[] uuid = c.getIntArray("UUID");
        if (uuid.length == 4) {
            mob.restoreUuid(uuidFromInts(uuid));
        }

        if (c.contains("Health")) {
            float health = c.getFloat("Health");
            if (health > 0f) {
                mob.setHealth(health);
            }
        }

        if (mob instanceof PathfinderMob pathfinder) {
            NbtList motion = c.getList("Motion");
            if (motion != null && motion.size() == 3) {
                pathfinder.setVelocity(doubleAt(motion, 0), doubleAt(motion, 1), doubleAt(motion, 2));
            }
            if (c.contains("OnGround")) {
                pathfinder.setOnGround(c.getBoolean("OnGround"));
            }
        }
        return mob;
    }

    private static NbtList doubleList(double a, double b, double c) {
        NbtList list = new NbtList(NbtType.DOUBLE);
        list.add(new NbtDouble(a));
        list.add(new NbtDouble(b));
        list.add(new NbtDouble(c));
        return list;
    }

    private static NbtList floatList(float a, float b) {
        NbtList list = new NbtList(NbtType.FLOAT);
        list.add(new NbtFloat(a));
        list.add(new NbtFloat(b));
        return list;
    }

    private static double doubleAt(NbtList list, int index) {
        if (list == null || index >= list.size()) {
            return 0.0;
        }
        return list.get(index) instanceof NbtDouble(double value) ? value : 0.0;
    }

    private static float floatAt(NbtList list, int index) {
        if (list == null || index >= list.size()) {
            return 0f;
        }
        return list.get(index) instanceof NbtFloat(float value) ? value : 0f;
    }

    static int[] uuidToInts(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        return new int[] {(int) (msb >> 32), (int) msb, (int) (lsb >> 32), (int) lsb};
    }

    static UUID uuidFromInts(int[] ints) {
        long msb = ((long) ints[0] << 32) | (ints[1] & 0xFFFFFFFFL);
        long lsb = ((long) ints[2] << 32) | (ints[3] & 0xFFFFFFFFL);
        return new UUID(msb, lsb);
    }
}

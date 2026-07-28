package fr.euphyllia.fidorial.server.adventure;

import fr.euphyllia.fidorial.server.entity.player.ServerPlayer;
import fr.euphyllia.fidorial.server.network.nbt.NbtBinaryTagBridge;
import fr.euphyllia.fidorial.server.world.nbt.Nbt;
import fr.euphyllia.fidorial.server.world.nbt.NbtIntArray;
import fr.euphyllia.fidorial.server.world.nbt.NbtString;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ClickCallbackManager implements AutoCloseable {

    public static final Key KEY = Key.key("fidorial", "click_callback");

    private final Map<UUID, ClickCallback<Audience>> persistentCallbacks = new ConcurrentHashMap<>(0);
    private final Map<UUID, ExpiringCallbackEntry> expiringCallbacks = new ConcurrentHashMap<>(0);
    private final ScheduledExecutorService cleanupService = Executors.newSingleThreadScheduledExecutor(
            r -> Thread.ofPlatform().name("fidorial-click-callback-sweeper").daemon(true).unstarted(r));

    public ClickCallbackManager() {
        this.cleanupService.scheduleAtFixedRate(this::refresh, 1, 1, TimeUnit.SECONDS);
    }

    private record ExpiringCallbackEntry(ClickCallback<Audience> callback, long expiresAt, AtomicInteger remainingUses) {
        private ExpiringCallbackEntry(ClickCallback<Audience> callback, long expiresAt, int remainingUses) {
            this(Objects.requireNonNull(callback, "callback"), expiresAt, new AtomicInteger(remainingUses));
        }

        boolean shouldRemove(final long now) {
            final int uses = this.remainingUses.get();
            return uses != ClickCallback.UNLIMITED_USES
                    && (uses <= 0 || now >= this.expiresAt);
        }

        void tryHandle(final Audience audience) {
            while (true) {
                final long now = System.nanoTime();
                if (now >= this.expiresAt) {
                    return;
                }

                final int currentUses = this.remainingUses.get();

                if (currentUses == ClickCallback.UNLIMITED_USES) {
                    this.callback.accept(audience);
                    return;
                }

                if (currentUses <= 0) {
                    return;
                }

                if (this.remainingUses.compareAndSet(currentUses, currentUses - 1)) {
                    this.callback.accept(audience);
                    return;
                }
            }
        }
    }

    private void refresh() {
        if (this.expiringCallbacks.isEmpty()) return;
        final long now = System.nanoTime();
        this.expiringCallbacks.values().removeIf(data -> data.shouldRemove(now));
    }

    @Override
    public void close() {
        this.cleanupService.shutdownNow();
    }

    public void handleClick(final ServerPlayer player, final Key key, final UUID uuid) {
        if (!KEY.equals(key)) return;

        final ClickCallback<Audience> persistent = this.persistentCallbacks.get(uuid);
        if (persistent != null) {
            persistent.accept(player);
            return;
        }
        final ExpiringCallbackEntry entry = this.expiringCallbacks.get(uuid);
        if (entry != null) {
            entry.tryHandle(player);
        }
    }

    private static long calculateExpiry(final ClickCallback.Options options) {
        try {
            final long now = System.nanoTime();
            final long lifetime = options.lifetime().toNanos();

            if (lifetime == Long.MAX_VALUE || Long.MAX_VALUE - now < lifetime) {
                return Long.MAX_VALUE;
            }

            return now + lifetime;
        } catch (final ArithmeticException ignored) {
            return Long.MAX_VALUE;
        }
    }

    public ClickEvent<?> addClickEvent(final ClickCallback<Audience> callback, final ClickCallback.Options options) {
        Objects.requireNonNull(callback, "callback");
        Objects.requireNonNull(options, "options");
        final UUID uuid = UUID.randomUUID();
        final int uses = options.uses();

        final long expiry = calculateExpiry(options);
        if (expiry == Long.MAX_VALUE && uses == ClickCallback.UNLIMITED_USES) {
            this.persistentCallbacks.put(uuid, callback);
        } else if (uses != 0 && expiry > System.nanoTime()) {
            this.expiringCallbacks.put(uuid, new ExpiringCallbackEntry(callback, expiry, uses));
        }

        return ClickEvent.custom(KEY, uuidToHolder(uuid));
    }

    public static BinaryTagHolder uuidToHolder(final UUID uuid) {
        final long msb = uuid.getMostSignificantBits();
        final long lsb = uuid.getLeastSignificantBits();
        final IntArrayBinaryTag tag = IntArrayBinaryTag.intArrayBinaryTag(
                (int) (msb >> 32), (int) msb, (int) (lsb >> 32), (int) lsb);
        try {
            return BinaryTagHolder.binaryTagHolder(TagStringIO.tagStringIO().asString(tag));
        } catch (final IOException e) {
            throw new IllegalStateException("Could not serialize UUID payload", e);
        }
    }

    public static UUID uuidFromPayload(Nbt nbt) {
        if (!(nbt instanceof NbtString(String value))) {
            throw new IllegalArgumentException("Expected string payload");
        }
        try {
            nbt = NbtBinaryTagBridge.toNbt(TagStringIO.tagStringIO().asTag(value));
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid SNBT payload", e);
        }

        if (!(nbt instanceof NbtIntArray(int[] v)) || v.length != 4) {
            throw new IllegalArgumentException("Expected a 4-element int array tag");
        }

        final long msb = ((long) v[0] << 32) | (v[1] & 0xFFFFFFFFL);
        final long lsb = ((long) v[2] << 32) | (v[3] & 0xFFFFFFFFL);
        return new UUID(msb, lsb);
    }
}

package fr.euphyllia.fidorial.testplugin;

import fr.euphyllia.fidorial.testplugin.command.ApiTestCommand;
import fr.euphyllia.fidorial.testplugin.command.BiomeCommand;
import fr.euphyllia.fidorial.testplugin.command.PregenCommand;
import fr.euphyllia.fidorial.testplugin.command.WorldgenCommand;
import fr.euphyllia.fidorial.testplugin.pregen.PregenTask;
import fr.euphyllia.fidorial.testplugin.terrain.TestBiomes;
import fr.euphyllia.fidorial.testplugin.worldgen.GeneratorSettings;
import fr.euphyllia.fidorial.testplugin.worldgen.OverworldGenerator;
import fr.fidorial.Server;
import fr.fidorial.command.CommandRegistry;
import fr.fidorial.command.CommandSender;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.player.BlockBreakEvent;
import fr.fidorial.event.player.BlockPlaceEvent;
import fr.fidorial.event.player.PlayerChatEvent;
import fr.fidorial.event.player.PlayerJoinEvent;
import fr.fidorial.event.player.PlayerLoginAttemptEvent;
import fr.fidorial.event.player.PlayerQuitEvent;
import fr.fidorial.event.server.ServerStartedEvent;
import fr.fidorial.event.server.ServerStatusRequestEvent;
import fr.fidorial.event.server.ServerStoppingEvent;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.plugin.PluginContext;
import fr.fidorial.service.ServicePriority;
import fr.fidorial.status.ServerStatus;
import fr.fidorial.world.generation.WorldGenerator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class TestPlugin implements Plugin {

    private static final String SEED_PROPERTY = "fidorial.worldgen.seed";

    private static final long DEFAULT_SEED = 20260716L;

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private final AtomicLong eventCount = new AtomicLong();
    private @Nullable PluginContext context;
    public @Nullable ComponentLogger logger;
    public @Nullable Server server;
    private volatile @Nullable PregenTask task;
    private @Nullable OverworldGenerator generator;

    public @Nullable OverworldGenerator generator() {
        return generator;
    }

    public PregenTask getTask() {
        return task;
    }

    public void setTask(final PregenTask task) {
        this.task = task;
    }

    public Server server() {
        return server;
    }

    public long eventCount() {
        return eventCount.get();
    }

    @Override
    public void onLoad(final PluginContext context) {
        this.context = context;
        this.logger = context.logger();
        this.server = context.server();

        TestBiomes.registerAll(context.server().biomes(), context.logger());

        final long seed = resolveSeed(context.logger());
        this.generator = new OverworldGenerator(GeneratorSettings.defaults(seed));

        context.services().register(WorldGenerator.class, generator, this);
        context.logger().info(
                "[TestPlugin] Generateur d'Overworld enregistre (seed={}, niveau de la mer={})",
                seed,
                generator.settings().seaLevel());

        final int[] spawn = generator.findSpawn(0, 0, 4000, -64, 319);
        if (spawn == null) {
            context.logger().warn("[TestPlugin] Aucun point d'apparition emerge trouve pres de l'origine.");
        } else {
            context.logger().info(
                    "[TestPlugin] Point d'apparition conseille : spawn-x={} spawn-y={} spawn-z={}",
                    spawn[0] + 0.5,
                    spawn[1],
                    spawn[2] + 0.5);
        }

        logger.info(
                "[TestPlugin] onLoad OK - id={} version={} dataFolder={}",
                context.meta().id(),
                context.meta().version(),
                context.dataFolder());
    }

    @Override
    public void onEnable() {
        logger.info("[TestPlugin] onEnable - demarrage des tests API");

        registerServices();
        registerEvents();
        registerCommands();
        TestPluginTranslations.register(context);

        logger.info("[TestPlugin] pret. Tape /apitest pour lancer les tests interactifs.");
    }

    @Override
    public void onDisable() {
        logger.info("[TestPlugin] onDisable - {} event(s) observe(s) pendant la session", eventCount.get());
        server.commands().unregisterNamespace(context.meta());
        TestBiomes.unregisterAll(server.biomes());
        TestPluginTranslations.unregister();
    }

    private static long resolveSeed(final ComponentLogger logger) {
        final String property = System.getProperty(SEED_PROPERTY);
        if (property == null || property.isBlank()) {
            return DEFAULT_SEED;
        }
        if (property.equalsIgnoreCase("random")) {
            final long random = new java.util.Random().nextLong();
            logger.info("[TestPlugin] Graine tiree au hasard : {}", random);
            return random;
        }
        try {
            return Long.parseLong(property.trim());
        } catch (final NumberFormatException invalid) {
            return property.hashCode();
        }
    }

    public void msg(final CommandSender sender, final String miniMessageText) {
        sender.sendMessage(MM.deserialize(miniMessageText));
    }

    private void msg(final Player player, final String miniMessageText) {
        player.sendMessage(MM.deserialize(miniMessageText));
    }

    private void registerServices() {
        final AtomicLong counter = new AtomicLong();
        final CounterService impl = new CounterService() {
            @Override
            public long increment() {
                return counter.incrementAndGet();
            }

            @Override
            public long current() {
                return counter.get();
            }
        };
        server.services().register(CounterService.class, impl, this, ServicePriority.NORMAL);
        logger.info(
                "[TestPlugin] ServiceRegistry: CounterService enregistre = {}",
                server.services().find(CounterService.class).isPresent());
    }

    private void registerEvents() {
        final var events = context.events();

        events.subscribe(ServerStatusRequestEvent.class, event -> {
            event.status(event.status().toBuilder()
                    .description(Component.text("HELLO!!!"))
                    .enforceSecureChat(true)
                    .samplePlayer(new ServerStatus.SamplePlayer("test", UUID.randomUUID()))
                    .maxPlayers(-999)
                    .players(999)
                    .version(new ServerStatus.Version(
                            "§aIDK §cXOXO",
                            event.status().version().protocolVersion()
                    ))
                    .build());
        });

        events.subscribe(ServerStartedEvent.class, e ->
                logger.info("[TestPlugin][event] ServerStartedEvent recu, version MC {}",
                        e.server().minecraftVersion()));

        events.subscribe(ServerStoppingEvent.class, e -> logger.info("[TestPlugin][event] ServerStoppingEvent recu"));

        events.subscribe(PlayerJoinEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] join de {}", e.player().name());
            msg(e.player(), "[TestPlugin] Bienvenue " + e.player().name() + " ! Tape /apitest pour tester l'API.");
        });

        events.subscribe(PlayerQuitEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] quit de {}", e.player().name());
        });

        events.subscribe(PlayerChatEvent.class, EventPriority.HIGH, e -> {
            eventCount.incrementAndGet();
            final String raw = PLAIN.serialize(e.message());
            if (raw.equalsIgnoreCase("!cancel")) {
                e.setCancelled(true);
                msg(e.player(), "[TestPlugin] Message annule (test Cancellable OK).");
            } else if (raw.startsWith("!upper ")) {
                e.setMessage(Component.text(raw.substring(7).toUpperCase(Locale.ROOT)));
            }
        });

        events.subscribe(BlockBreakEvent.class, e -> {
            eventCount.incrementAndGet();
            // logger.info("[TestPlugin][event] {} casse un bloc en {}", e.player().name(), e.position());
        });

        events.subscribe(BlockPlaceEvent.class, e -> {
            eventCount.incrementAndGet();
            //   logger.info("[TestPlugin][event] {} pose un bloc", e.player().name());
        });
        final boolean cancelLogin = false;
        events.subscribe(PlayerLoginAttemptEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] login attempt de {} (auth={})", e.profile().name(), e.authenticated());
            if (!cancelLogin) return;
            e.setCancelled(cancelLogin);
            e.refuse(Component.text("Serveur en maintenance", NamedTextColor.RED));
        });
    }

    private void registerCommands() {
        final CommandRegistry registry = server.commands();
        registry.register(context.meta(), new PregenCommand(this).create());
        registry.register(context.meta(), new ApiTestCommand(this).create());
        registry.register(context.meta(), new BiomeCommand(this).create());
        registry.register(context.meta(), new WorldgenCommand(this).create());
    }
}

package fr.euphyllia.fidorial.testplugin;

import fr.euphyllia.fidorial.testplugin.command.ApiTestCommand;
import fr.euphyllia.fidorial.testplugin.command.PregenCommand;
import fr.fidorial.Server;
import fr.fidorial.command.CommandRegistry;
import fr.fidorial.command.CommandSender;
import fr.fidorial.entity.Player;
import fr.fidorial.event.EventPriority;
import fr.fidorial.event.player.BlockBreakEvent;
import fr.fidorial.event.player.BlockPlaceEvent;
import fr.fidorial.event.player.PlayerChatEvent;
import fr.fidorial.event.player.PlayerJoinEvent;
import fr.fidorial.event.player.PlayerQuitEvent;
import fr.fidorial.event.server.ServerStartedEvent;
import fr.fidorial.event.server.ServerStoppingEvent;
import fr.fidorial.plugin.Plugin;
import fr.fidorial.plugin.PluginContext;
import fr.fidorial.service.ServicePriority;
import fr.fidorial.world.generation.WorldGenerator;
import fr.euphyllia.fidorial.testplugin.pregen.PregenTask;
import fr.euphyllia.fidorial.testplugin.terrain.HillsGenerator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

public final class TestPlugin implements Plugin {

    private static final long SEED = 20260716L;
    private static final int BASE_HEIGHT = 64;
    private static final int AMPLITUDE = 24;
    private static final int SEA_LEVEL = 60;

    private static final MiniMessage MM = MiniMessage.miniMessage();
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();

    private final AtomicLong eventCount = new AtomicLong();
    private PluginContext context;
    public ComponentLogger logger;
    public Server server;
    private volatile PregenTask task;

    public PregenTask getTask() {
        return task;
    }

    public void setTask(PregenTask task) {
        this.task = task;
    }

    public Server server() {
        return server;
    }

    public long eventCount() {
        return eventCount.get();
    }

    @Override
    public void onLoad(PluginContext context) {
        this.context = context;
        this.logger = context.logger();
        this.server = context.server();

        context.services().register(WorldGenerator.class,
                new HillsGenerator(SEED, BASE_HEIGHT, AMPLITUDE, SEA_LEVEL), this);
        context.logger().info("Generateur de collines enregistre (seed={})", SEED);

        logger.info("[TestPlugin] onLoad OK - id={} version={} dataFolder={}",
                context.meta().id(), context.meta().version(), context.dataFolder());
    }

    @Override
    public void onEnable() {
        logger.info("[TestPlugin] onEnable - demarrage des tests API");

        registerServices();
        registerEvents();
        registerCommands();

        logger.info("[TestPlugin] pret. Tape /apitest pour lancer les tests interactifs.");
    }

    @Override
    public void onDisable() {
        logger.info("[TestPlugin] onDisable - {} event(s) observe(s) pendant la session", eventCount.get());
        server.commands().unregister("apitest");
    }

    private void msg(CommandSender sender, String miniMessageText) {
        sender.sendMessage(MM.deserialize(miniMessageText));
    }

    private void msg(Player player, String miniMessageText) {
        player.sendMessage(MM.deserialize(miniMessageText));
    }

    private void registerServices() {
        AtomicLong counter = new AtomicLong();
        CounterService impl = new CounterService() {
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
        logger.info("[TestPlugin] ServiceRegistry: CounterService enregistre = {}",
                server.services().find(CounterService.class).isPresent());
    }

    private void registerEvents() {
        var events = context.events();

        events.subscribe(ServerStartedEvent.class, e ->
                logger.info("[TestPlugin][event] ServerStartedEvent recu, version MC {}",
                        e.server().minecraftVersion()));

        events.subscribe(ServerStoppingEvent.class, e ->
                logger.info("[TestPlugin][event] ServerStoppingEvent recu"));

        events.subscribe(PlayerJoinEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] join de {}", e.player().name());
            msg(e.player(), "[TestPlugin] Bienvenue " + e.player().name()
                    + " ! Tape /apitest pour tester l'API.");
        });

        events.subscribe(PlayerQuitEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] quit de {}", e.player().name());
        });

        events.subscribe(PlayerChatEvent.class, EventPriority.HIGH, e -> {
            eventCount.incrementAndGet();
            String raw = PLAIN.serialize(e.message());
            if (raw.equalsIgnoreCase("!cancel")) {
                e.setCancelled(true);
                msg(e.player(), "[TestPlugin] Message annule (test Cancellable OK).");
            } else if (raw.startsWith("!upper ")) {
                e.setMessage(Component.text(raw.substring(7).toUpperCase(Locale.ROOT)));
            }
        });

        events.subscribe(BlockBreakEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] {} casse un bloc en {}", e.player().name(), e.position());
        });

        events.subscribe(BlockPlaceEvent.class, e -> {
            eventCount.incrementAndGet();
            logger.info("[TestPlugin][event] {} pose un bloc", e.player().name());
        });
    }

    private void registerCommands() {
        final CommandRegistry registry = server.commands();
        registry.register(
                registry.metaBuilder("pregen").build(),
                new PregenCommand(this).create()
        );
        registry.register(
                registry.metaBuilder("apitest").build(),
                new ApiTestCommand(this).create()
        );
    }
}

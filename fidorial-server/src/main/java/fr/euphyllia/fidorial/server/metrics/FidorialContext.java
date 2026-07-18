package fr.euphyllia.fidorial.server.metrics;

import dev.faststats.Metrics;
import dev.faststats.SimpleContext;
import dev.faststats.SimpleMetrics;
import dev.faststats.Token;
import dev.faststats.config.SimpleConfig;
import dev.faststats.internal.LoggerFactory;
import dev.faststats.internal.PlatformLoggerFactory;
import fr.euphyllia.fidorial.server.FidorialServer;
import org.jetbrains.annotations.Contract;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public final class FidorialContext extends SimpleContext {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private FidorialContext(
            Factory factory,
            LoggerFactory loggerFactory,
            @Token String token
    ) {
        super(
                factory,
                loggerFactory,
                SimpleConfig.read(
                        Path.of("faststats")
                                .resolve("config.properties"),
                        loggerFactory
                ),
                "fidorial",
                token
        );

        initializeServices(factory);
    }

    @Override
    @Contract(value = " -> new", pure = true)
    protected Metrics.Factory metricsFactory() {
        return new SimpleMetrics.Factory(this) {
            @Override
            public Metrics create() throws IllegalStateException {
                return new FidorialMetricsImpl(this);
            }
        };
    }

    @Override
    protected boolean preSubmissionStart() {
        return ((SimpleConfig) getConfig()).preSubmissionStart(this);
    }

    @Override
    public String getProjectName() {
        return "Fidorial";
    }

    @Override
    protected void scheduleAtFixedRate(final Runnable task, final long initialDelay, final long period, final TimeUnit unit) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    public static final class Factory extends SimpleContext.Factory<FidorialContext, Factory> {
        private final @Token String token;

        public Factory(@Token final String token) {
            this.token = token;
        }

        @Override
        public FidorialContext create() {
            final var loggerFactory = new PlatformLoggerFactory((level, throwable, message) -> {

                switch (level) {

                    case INFO -> {
                        if (throwable == null)
                            FidorialServer.LOGGER.info(message);
                        else
                            FidorialServer.LOGGER.info(message, throwable);
                    }

                    case WARN -> {
                        if (throwable == null)
                            FidorialServer.LOGGER.warn(message);
                        else
                            FidorialServer.LOGGER.warn(message, throwable);
                    }

                    case ERROR -> {
                        if (throwable == null)
                            FidorialServer.LOGGER.error(message);
                        else
                            FidorialServer.LOGGER.error(message, throwable);
                    }
                }
            });
            return new FidorialContext(this, loggerFactory, token);
        }
    }
}

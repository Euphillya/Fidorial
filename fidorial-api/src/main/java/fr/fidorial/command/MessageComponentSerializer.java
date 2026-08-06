package fr.fidorial.command;

import com.mojang.brigadier.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * A component serializer for converting between {@link Message} and {@link Component}.
 * <p>
 * This interface is derived from Paper's <a href="https://github.com/PaperMC/Paper/blob/main/paper-api/src/main/java/io/papermc/paper/command/brigadier/MessageComponentSerializer.java">MessageComponentSerializer</a>.
 * Originally contributed in <a href="https://github.com/PaperMC/Paper/pull/8235">#8235</a>, licensed under the MIT license.
 *
 * @since 0.1.0
 */
@ApiStatus.NonExtendable
public interface MessageComponentSerializer extends ComponentSerializer<Component, Component, Message> {

    /**
     * A component serializer for converting between {@link Message} and {@link Component}.
     *
     * @return serializer instance
     * @since 0.1.0
     */
    static MessageComponentSerializer message() {
        final class Holder {
            static final Optional<MessageComponentSerializer> PROVIDER = ServiceLoader.load(
                            MessageComponentSerializer.class, MessageComponentSerializer.class.getClassLoader())
                    .findFirst();
        }
        return Holder.PROVIDER.orElseThrow();
    }
}

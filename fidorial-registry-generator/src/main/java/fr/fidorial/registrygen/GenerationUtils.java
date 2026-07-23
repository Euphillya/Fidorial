package fr.fidorial.registrygen;

import java.util.Locale;

/**
 * Utilities utilized during the generation of the actual class files.
 *
 * * @since 0.1.0
 */
public final class GenerationUtils {

  private GenerationUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Converts a Minecraft identifier into a Java constant name.
   *
   * minecraft:attack_damage -> ATTACK_DAMAGE
   * minecraft:oak_log -> OAK_LOG
   */
  public static String constantName(final String identifier) {

    final String path = path(identifier);

    String result = path
            .replace('/', '_')
            .replace('-', '_')
            .replace('.', '_')
            .replace(':', '_')
            .toUpperCase(Locale.ROOT);

    result = result.replaceAll("[^A-Z0-9_]", "_");
    result = result.replaceAll("_+", "_");

    if (!result.isEmpty() && Character.isDigit(result.charAt(0))) {
      result = "_" + result;
    }

    return result;
  }

  /**
   * Converts a Minecraft identifier into PascalCase.
   *
   * minecraft:attack_damage -> AttackDamage
   * minecraft:oak_log -> OakLog
   */
  public static String className(final String identifier) {

    final String path = path(identifier);

    final StringBuilder builder = new StringBuilder();

    boolean capitalize = true;

    for (final char c : path.toCharArray()) {

      if (c == '_' || c == '-' || c == '/' || c == '.') {
        capitalize = true;
        continue;
      }

      builder.append((capitalize)? Character.toUpperCase(c) : c);

      capitalize = false;
    }

    return builder.toString();
  }

  /**
   * Returns the path portion of a Minecraft identifier.
   *
   * minecraft:oak_log -> oak_log
   */
  public static String path(final String identifier) {

    final int colon = identifier.indexOf(':');

    return (colon >= 0)? identifier.substring(colon + 1) : identifier;
  }
}
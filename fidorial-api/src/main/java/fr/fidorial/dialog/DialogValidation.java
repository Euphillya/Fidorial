package fr.fidorial.dialog;

public class DialogValidation {

    private DialogValidation() {
        throw new UnsupportedOperationException("DialogValidation cannot be instantiated.");
    }

    public static int width(final int value, final int max, final String name) {
        if (value < 1 || value > max) {
            throw new IllegalArgumentException(name + " must be between 1 and " + max + ", was " + value);
        }
        return value;
    }

    public static String inputKey(final String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("An input key cannot be empty");
        }
        for (int i = 0; i < key.length(); i++) {
            final char c = key.charAt(i);
            final boolean allowed = (c >= 'a' && c <= 'z')
                    || (c >= 'A' && c <= 'Z')
                    || (c >= '0' && c <= '9')
                    || c == '_';
            if (!allowed) {
                throw new IllegalArgumentException(
                        "An input key may only contain letters, digits and '_', was '" + key + "'");
            }
        }
        return key;
    }

    public static int positive(final int value, final String name) {
        if (value < 1) {
            throw new IllegalArgumentException(name + " must be positive, was " + value);
        }
        return value;
    }
}

package fr.fidorial.dialog;

import java.util.Objects;

/**
 * A dialog with two buttons in its footer.
 *
 * @param base the shared title, contents and behaviour
 * @param yes  the button for the positive outcome
 * @param no   the button for the negative outcome
 * @sinceMinecraft 1.21.6
 * @since 0.1.0
 */
public record ConfirmationDialog(DialogBase base, DialogActionButton yes, DialogActionButton no)
        implements DialogDefinition {

    /**
     * @param base the shared title, contents and behaviour
     * @param yes  the button for the positive outcome
     * @param no   the button for the negative outcome
     * @since 0.1.0
     */
    public ConfirmationDialog {
        Objects.requireNonNull(base, "base");
        Objects.requireNonNull(yes, "yes");
        Objects.requireNonNull(no, "no");
    }
}

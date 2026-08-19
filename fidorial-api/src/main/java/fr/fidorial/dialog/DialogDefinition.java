package fr.fidorial.dialog;

public sealed interface DialogDefinition extends Dialog
        permits NoticeDialog, ConfirmationDialog, MultiActionDialog, ServerLinksDialog, DialogListDialog {

    /**
     * {@return the title, contents and behaviour shared by every dialog type}
     *
     * @since 0.1.0
     */
    DialogBase base();
}

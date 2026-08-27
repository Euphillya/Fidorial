package fr.euphyllia.fidorial.server.codecs.dialog;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.euphyllia.fidorial.server.codecs.DispatchCodecs;
import fr.euphyllia.fidorial.server.codecs.adventure.StyleCodecs;
import fr.fidorial.dialog.ConfirmationDialog;
import fr.fidorial.dialog.Dialog;
import fr.fidorial.dialog.DialogAction;
import fr.fidorial.dialog.DialogActionButton;
import fr.fidorial.dialog.DialogAfterAction;
import fr.fidorial.dialog.DialogBase;
import fr.fidorial.dialog.DialogBody;
import fr.fidorial.dialog.DialogDefinition;
import fr.fidorial.dialog.DialogInput;
import fr.fidorial.dialog.DialogListDialog;
import fr.fidorial.dialog.DialogReference;
import fr.fidorial.dialog.MultiActionDialog;
import fr.fidorial.dialog.NoticeDialog;
import fr.fidorial.dialog.ServerLinksDialog;
import io.papermc.adventurex.nbt.dfu.BinaryTagOps;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.event.ClickEvent;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.euphyllia.fidorial.server.codecs.CommonCodecs.KEY_CODEC;
import static fr.euphyllia.fidorial.server.codecs.adventure.ComponentCodecs.COMPONENT_CODEC;
import static fr.euphyllia.fidorial.server.codecs.adventure.NbtCodecs.COMPOUND_BINARY_TAG_CODEC;


public final class DialogCodecs {

    public static final Codec<DialogAction> ACTION_CODEC = Codec.STRING.dispatch(
            "type",
            DialogCodecs::actionName,
            DialogCodecs::actionMapCodecFor);

    public static final Codec<DialogBody> BODY_CODEC;
    public static final Codec<DialogInput> INPUT_CODEC;
    public static final Codec<DialogDefinition> DEFINITION_CODEC;
    public static final Codec<Dialog> DIALOG_CODEC;

    private static final Codec<DialogAfterAction> AFTER_ACTION_CODEC =
            Codec.STRING.comapFlatMap(DialogCodecs::afterActionById, DialogAfterAction::id);

    private static final Codec<DialogActionButton> BUTTON_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            COMPONENT_CODEC.fieldOf("label").forGetter(DialogActionButton::label),
            COMPONENT_CODEC.optionalFieldOf("tooltip").forGetter(b -> Optional.ofNullable(b.tooltip())),
            Codec.intRange(1, DialogActionButton.MAX_WIDTH)
                    .optionalFieldOf("width", DialogActionButton.DEFAULT_WIDTH)
                    .forGetter(DialogActionButton::width),
            ACTION_CODEC.optionalFieldOf("action").forGetter(b -> Optional.ofNullable(b.action()))
    ).apply(instance, (label, tooltip, width, action) ->
            new DialogActionButton(label, tooltip.orElse(null), width, action.orElse(null))));

    private DialogCodecs() {
        throw new UnsupportedOperationException("DialogCodecs cannot be instantiated.");
    }

    private static final MapCodec<DialogBody.PlainMessage> PLAIN_MESSAGE_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    COMPONENT_CODEC.fieldOf("contents").forGetter(DialogBody.PlainMessage::contents),
                    Codec.intRange(1, DialogBody.MAX_WIDTH)
                            .optionalFieldOf("width", DialogBody.DEFAULT_WIDTH)
                            .forGetter(DialogBody.PlainMessage::width)
            ).apply(instance, DialogBody.PlainMessage::new));

    private static final MapCodec<DialogBody.Item> ITEM_BODY_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    DialogItemCodecs.ITEM_STACK_CODEC.fieldOf("item").forGetter(DialogBody.Item::item),
                    PLAIN_MESSAGE_CODEC.codec().optionalFieldOf("description")
                            .forGetter(b -> Optional.ofNullable(b.description())),
                    Codec.BOOL.optionalFieldOf("show_decoration", true).forGetter(DialogBody.Item::showDecoration),
                    Codec.BOOL.optionalFieldOf("show_tooltip", true).forGetter(DialogBody.Item::showTooltip),
                    Codec.intRange(1, DialogBody.Item.MAX_ITEM_SIZE)
                            .optionalFieldOf("width", DialogBody.Item.DEFAULT_ITEM_SIZE)
                            .forGetter(DialogBody.Item::width),
                    Codec.intRange(1, DialogBody.Item.MAX_ITEM_SIZE)
                            .optionalFieldOf("height", DialogBody.Item.DEFAULT_ITEM_SIZE)
                            .forGetter(DialogBody.Item::height)
            ).apply(instance, (item, description, decoration, tooltip, width, height) ->
                    new DialogBody.Item(item, description.orElse(null), decoration, tooltip, width, height)));

    private static final Codec<DialogInput.Text.Multiline> MULTILINE_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.optionalFieldOf("max_lines").forGetter(m -> Optional.ofNullable(m.maxLines())),
                    Codec.intRange(1, DialogInput.Text.Multiline.MAX_HEIGHT)
                            .optionalFieldOf("height").forGetter(m -> Optional.ofNullable(m.height()))
            ).apply(instance, (maxLines, height) ->
                    new DialogInput.Text.Multiline(maxLines.orElse(null), height.orElse(null))));

    private static final MapCodec<DialogInput.Text> TEXT_INPUT_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(DialogInput.Text::key),
                    COMPONENT_CODEC.fieldOf("label").forGetter(DialogInput.Text::label),
                    Codec.intRange(1, DialogInput.MAX_WIDTH)
                            .optionalFieldOf("width", DialogInput.DEFAULT_WIDTH)
                            .forGetter(DialogInput.Text::width),
                    Codec.BOOL.optionalFieldOf("label_visible", true).forGetter(DialogInput.Text::labelVisible),
                    Codec.STRING.optionalFieldOf("initial", "").forGetter(DialogInput.Text::initial),
                    Codec.INT.optionalFieldOf("max_length", DialogInput.Text.DEFAULT_MAX_LENGTH)
                            .forGetter(DialogInput.Text::maxLength),
                    MULTILINE_CODEC.optionalFieldOf("multiline").forGetter(t -> Optional.ofNullable(t.multiline()))
            ).apply(instance, (key, label, width, visible, initial, maxLength, multiline) ->
                    new DialogInput.Text(key, label, width, visible, initial, maxLength, multiline.orElse(null))));

    private static final MapCodec<DialogInput.Bool> BOOL_INPUT_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(DialogInput.Bool::key),
                    COMPONENT_CODEC.fieldOf("label").forGetter(DialogInput.Bool::label),
                    Codec.BOOL.optionalFieldOf("initial", false).forGetter(DialogInput.Bool::initial),
                    Codec.STRING.optionalFieldOf("on_true", "true").forGetter(DialogInput.Bool::onTrue),
                    Codec.STRING.optionalFieldOf("on_false", "false").forGetter(DialogInput.Bool::onFalse)
            ).apply(instance, DialogInput.Bool::new));

    private static final Codec<DialogInput.SingleOption.Entry> OPTION_ENTRY_CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("id").forGetter(DialogInput.SingleOption.Entry::id),
                    COMPONENT_CODEC.optionalFieldOf("display").forGetter(e -> Optional.ofNullable(e.display())),
                    Codec.BOOL.optionalFieldOf("initial", false).forGetter(DialogInput.SingleOption.Entry::initial)
            ).apply(instance, (id, display, initial) ->
                    new DialogInput.SingleOption.Entry(id, display.orElse(null), initial)));

    private static final MapCodec<DialogInput.SingleOption> SINGLE_OPTION_INPUT_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(DialogInput.SingleOption::key),
                    COMPONENT_CODEC.fieldOf("label").forGetter(DialogInput.SingleOption::label),
                    OPTION_ENTRY_CODEC.listOf().fieldOf("options").forGetter(DialogInput.SingleOption::options),
                    Codec.BOOL.optionalFieldOf("label_visible", true).forGetter(DialogInput.SingleOption::labelVisible),
                    Codec.intRange(1, DialogInput.MAX_WIDTH)
                            .optionalFieldOf("width", DialogInput.DEFAULT_WIDTH)
                            .forGetter(DialogInput.SingleOption::width)
            ).apply(instance, DialogInput.SingleOption::new));

    private static final MapCodec<DialogInput.NumberRange> NUMBER_RANGE_INPUT_CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(DialogInput.NumberRange::key),
                    COMPONENT_CODEC.fieldOf("label").forGetter(DialogInput.NumberRange::label),
                    Codec.STRING.optionalFieldOf("label_format", DialogInput.NumberRange.DEFAULT_LABEL_FORMAT)
                            .forGetter(DialogInput.NumberRange::labelFormat),
                    Codec.intRange(1, DialogInput.MAX_WIDTH)
                            .optionalFieldOf("width", DialogInput.DEFAULT_WIDTH)
                            .forGetter(DialogInput.NumberRange::width),
                    Codec.FLOAT.fieldOf("start").forGetter(DialogInput.NumberRange::start),
                    Codec.FLOAT.fieldOf("end").forGetter(DialogInput.NumberRange::end),
                    Codec.FLOAT.optionalFieldOf("step").forGetter(n -> Optional.ofNullable(n.step())),
                    Codec.FLOAT.optionalFieldOf("initial").forGetter(n -> Optional.ofNullable(n.initial()))
            ).apply(instance, (key, label, format, width, start, end, step, initial) ->
                    new DialogInput.NumberRange(
                            key, label, format, width, start, end, step.orElse(null), initial.orElse(null))));

    static {
        BODY_CODEC = DispatchCodecs.<DialogBody>matcher("type", List.of(
                DispatchCodecs.Variant.of(
                        "plain_message", null, true, DialogBody.PlainMessage.class, PLAIN_MESSAGE_CODEC),
                DispatchCodecs.Variant.of(
                        "item", null, true, DialogBody.Item.class, ITEM_BODY_CODEC)
        )).codec();

        INPUT_CODEC = DispatchCodecs.<DialogInput>matcher("type", List.of(
                DispatchCodecs.Variant.of(
                        "text", null, true, DialogInput.Text.class, TEXT_INPUT_CODEC),
                DispatchCodecs.Variant.of(
                        "boolean", null, true, DialogInput.Bool.class, BOOL_INPUT_CODEC),
                DispatchCodecs.Variant.of(
                        "single_option", null, true,
                        DialogInput.SingleOption.class, SINGLE_OPTION_INPUT_CODEC),
                DispatchCodecs.Variant.of(
                        "number_range", null, true,
                        DialogInput.NumberRange.class, NUMBER_RANGE_INPUT_CODEC)
        )).codec();

        DEFINITION_CODEC = Codec.<DialogDefinition>recursive("fidorial:dialog", self -> {
            final Codec<Dialog> dialog = eitherReferenceOr(self);
            return DispatchCodecs.<DialogDefinition>matcher("type", List.of(
                    DispatchCodecs.Variant.of(
                            "notice", null, true, NoticeDialog.class, noticeCodec()),
                    DispatchCodecs.Variant.of(
                            "confirmation", null, true,
                            ConfirmationDialog.class, confirmationCodec()),
                    DispatchCodecs.Variant.of(
                            "multi_action", null, true,
                            MultiActionDialog.class, multiActionCodec()),
                    DispatchCodecs.Variant.of(
                            "server_links", null, true,
                            ServerLinksDialog.class, serverLinksCodec()),
                    DispatchCodecs.Variant.of(
                            "dialog_list", null, true,
                            DialogListDialog.class, dialogListCodec(dialog))
            )).codec();
        });

        DIALOG_CODEC = eitherReferenceOr(DEFINITION_CODEC);
    }

    private static MapCodec<DialogBase> baseCodec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                COMPONENT_CODEC.fieldOf("title").forGetter(DialogBase::title),
                COMPONENT_CODEC.optionalFieldOf("external_title")
                        .forGetter(b -> Optional.ofNullable(b.externalTitle())),
                bodyListCodec().optionalFieldOf("body", List.of()).forGetter(DialogBase::body),
                INPUT_CODEC.listOf().optionalFieldOf("inputs", List.of()).forGetter(DialogBase::inputs),
                Codec.BOOL.optionalFieldOf("can_close_with_escape", true).forGetter(DialogBase::canCloseWithEscape),
                Codec.BOOL.optionalFieldOf("pause", true).forGetter(DialogBase::pause),
                AFTER_ACTION_CODEC.optionalFieldOf("after_action", DialogAfterAction.CLOSE)
                        .forGetter(DialogBase::afterAction)
        ).apply(instance, (title, external, body, inputs, escape, pause, after) ->
                new DialogBase(title, external.orElse(null), body, inputs, escape, pause, after)));
    }

    private static MapCodec<NoticeDialog> noticeCodec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                baseCodec().forGetter(NoticeDialog::base),
                BUTTON_CODEC.optionalFieldOf("action", NoticeDialog.DEFAULT_ACTION).forGetter(NoticeDialog::action)
        ).apply(instance, NoticeDialog::new));
    }

    private static MapCodec<ConfirmationDialog> confirmationCodec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                baseCodec().forGetter(ConfirmationDialog::base),
                BUTTON_CODEC.fieldOf("yes").forGetter(ConfirmationDialog::yes),
                BUTTON_CODEC.fieldOf("no").forGetter(ConfirmationDialog::no)
        ).apply(instance, ConfirmationDialog::new));
    }

    private static MapCodec<MultiActionDialog> multiActionCodec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                baseCodec().forGetter(MultiActionDialog::base),
                BUTTON_CODEC.listOf().fieldOf("actions").forGetter(MultiActionDialog::actions),
                Codec.INT.optionalFieldOf("columns", MultiActionDialog.DEFAULT_COLUMNS)
                        .forGetter(MultiActionDialog::columns),
                BUTTON_CODEC.optionalFieldOf("exit_action").forGetter(d -> Optional.ofNullable(d.exitAction()))
        ).apply(instance, (base, actions, columns, exit) ->
                new MultiActionDialog(base, actions, columns, exit.orElse(null))));
    }

    private static MapCodec<ServerLinksDialog> serverLinksCodec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                baseCodec().forGetter(ServerLinksDialog::base),
                BUTTON_CODEC.optionalFieldOf("exit_action").forGetter(d -> Optional.ofNullable(d.exitAction())),
                Codec.INT.optionalFieldOf("columns", ServerLinksDialog.DEFAULT_COLUMNS)
                        .forGetter(ServerLinksDialog::columns),
                Codec.intRange(1, DialogActionButton.MAX_WIDTH)
                        .optionalFieldOf("button_width", DialogActionButton.DEFAULT_WIDTH)
                        .forGetter(ServerLinksDialog::buttonWidth)
        ).apply(instance, (base, exit, columns, width) ->
                new ServerLinksDialog(base, exit.orElse(null), columns, width)));
    }

    private static MapCodec<DialogListDialog> dialogListCodec(final Codec<Dialog> nested) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                baseCodec().forGetter(DialogListDialog::base),
                nested.listOf().fieldOf("dialogs").forGetter(DialogListDialog::dialogs),
                BUTTON_CODEC.optionalFieldOf("exit_action").forGetter(d -> Optional.ofNullable(d.exitAction())),
                Codec.INT.optionalFieldOf("columns", DialogListDialog.DEFAULT_COLUMNS)
                        .forGetter(DialogListDialog::columns),
                Codec.intRange(1, DialogActionButton.MAX_WIDTH)
                        .optionalFieldOf("button_width", DialogActionButton.DEFAULT_WIDTH)
                        .forGetter(DialogListDialog::buttonWidth)
        ).apply(instance, (base, dialogs, exit, columns, width) ->
                new DialogListDialog(base, dialogs, exit.orElse(null), columns, width)));
    }

    private static Codec<List<DialogBody>> bodyListCodec() {
        return Codec.either(BODY_CODEC.listOf(), BODY_CODEC).xmap(
                either -> either.<List<DialogBody>>map(list -> list, List::of),
                Either::left);
    }

    private static Codec<Dialog> eitherReferenceOr(final Codec<DialogDefinition> definition) {
        return Codec.either(KEY_CODEC, definition).xmap(
                either -> either.<Dialog>map(Dialog::reference, dialog -> dialog),
                dialog -> {
                    if (dialog instanceof final DialogReference reference) {
                        return Either.left(reference.key());
                    }
                    return Either.right((DialogDefinition) dialog);
                });
    }

    private static String actionName(final DialogAction action) {
        return switch (action) {
            case final DialogAction.Static value -> StyleCodecs.clickActionName(value.event());
            case DialogAction.ShowDialog _ -> "show_dialog";
            case DialogAction.DynamicRunCommand _ -> "dynamic/run_command";
            case DialogAction.DynamicCustom _ -> "dynamic/custom";
        };
    }

    private static MapCodec<? extends DialogAction> actionMapCodecFor(final String type) {
        return switch (type) {
            case "show_dialog" -> showDialogCodec();
            case "dynamic/run_command" -> dynamicRunCommandCodec();
            case "dynamic/custom" -> dynamicCustomCodec();
            default -> staticActionCodec(type);
        };
    }

    private static MapCodec<DialogAction.ShowDialog> showDialogCodec() {
        return DIALOG_CODEC.fieldOf("dialog")
                .xmap(DialogAction.ShowDialog::new, DialogAction.ShowDialog::dialog);
    }

    private static MapCodec<DialogAction.DynamicRunCommand> dynamicRunCommandCodec() {
        return Codec.STRING.fieldOf("template")
                .xmap(DialogAction.DynamicRunCommand::new, DialogAction.DynamicRunCommand::template);
    }

    private static MapCodec<DialogAction.DynamicCustom> dynamicCustomCodec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                KEY_CODEC.fieldOf("id").forGetter(DialogAction.DynamicCustom::id),
                COMPOUND_BINARY_TAG_CODEC.optionalFieldOf("additions")
                        .forGetter(action -> Optional.ofNullable(action.additions()))
        ).apply(instance, (id, additions) -> new DialogAction.DynamicCustom(id, additions.orElse(null))));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static MapCodec<DialogAction> staticActionCodec(final String type) {
        final MapCodec raw = StyleCodecs.clickMapCodecFor(type);
        final MapCodec<ClickEvent<?>> codec = (MapCodec<ClickEvent<?>>) raw;
        return codec.<DialogAction>xmap(DialogAction::of, action -> ((DialogAction.Static) action).event());
    }

    private static DataResult<DialogAfterAction> afterActionById(final String id) {
        for (final DialogAfterAction value : DialogAfterAction.values()) {
            if (value.id().equals(id)) {
                return DataResult.success(value);
            }
        }
        return DataResult.error(() -> "Unknown after_action: " + id);
    }

    public static CompoundBinaryTag toNbt(final DialogDefinition dialog) {
        Objects.requireNonNull(dialog, "dialog");
        final BinaryTag tag = DEFINITION_CODEC
                .encodeStart(BinaryTagOps.binaryTagOps(), dialog)
                .getOrThrow(message -> new IllegalArgumentException("Failed to encode dialog: " + message));
        if (!(tag instanceof final CompoundBinaryTag compound)) {
            throw new IllegalArgumentException("A dialog must encode to a compound tag, got " + tag.type());
        }
        return compound;
    }

    public static DialogDefinition fromJson(final Key key, final String json) {
        final JsonElement element;
        try {
            element = JsonParser.parseString(json);
        } catch (final RuntimeException exception) {
            throw new IllegalArgumentException("Dialog " + key.asString() + " is not valid JSON", exception);
        }

        return DEFINITION_CODEC
                .parse(JsonOps.INSTANCE, element)
                .getOrThrow(message -> new IllegalArgumentException(
                        "Failed to read dialog " + key.asString() + ": " + message));
    }

}

package fr.fidorial.world.block.data.type;

import fr.fidorial.world.block.data.Powerable;

public interface NoteBlock extends Powerable {

    @Override
    default NoteBlock setPowered(boolean powered) {
        return (NoteBlock) Powerable.super.setPowered(powered);
    }

    default String getInstrument() {
        return get("instrument");
    }

    default NoteBlock setInstrument(String instrument) {
        return (NoteBlock) with("instrument", instrument);
    }

    default int getNote() {
        return Integer.parseInt(get("note"));
    }

    default NoteBlock setNote(int note) {
        return (NoteBlock) with("note", String.valueOf(note));
    }
}

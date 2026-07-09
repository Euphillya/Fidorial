package fr.euphyllia.fidorial.server.network.codec;

import fr.euphyllia.fidorial.server.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class FrameEncoder extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes();
        out.ensureWritable(VarInts.varIntSize(length) + length);
        VarInts.writeVarInt(out, length);
        out.writeBytes(msg);
    }
}

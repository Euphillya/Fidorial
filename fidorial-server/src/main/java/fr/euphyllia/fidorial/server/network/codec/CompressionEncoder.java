package fr.euphyllia.fidorial.server.network.codec;

import fr.euphyllia.fidorial.server.network.VarInts;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.zip.Deflater;

public final class CompressionEncoder extends MessageToByteEncoder<ByteBuf> {

    private final int threshold;
    private final Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION);

    public CompressionEncoder(int threshold) {
        this.threshold = threshold;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int uncompressed = msg.readableBytes();
        if (uncompressed < threshold) {
            VarInts.writeVarInt(out, 0);
            out.writeBytes(msg);
            return;
        }

        byte[] input = new byte[uncompressed];
        msg.readBytes(input);

        VarInts.writeVarInt(out, uncompressed);
        deflater.setInput(input);
        deflater.finish();
        byte[] buffer = new byte[8192];
        while (!deflater.finished()) {
            int n = deflater.deflate(buffer);
            out.writeBytes(buffer, 0, n);
        }
        deflater.reset();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        deflater.end();
    }
}

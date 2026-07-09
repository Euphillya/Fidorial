package fr.euphyllia.fidorial.server.network.codec;

import fr.euphyllia.fidorial.server.network.VarInts;
import fr.euphyllia.fidorial.server.protocol.ProtocolConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;
import java.util.zip.Inflater;

public final class CompressionDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final int threshold;
    private final Inflater inflater = new Inflater();

    public CompressionDecoder(int threshold) {
        this.threshold = threshold;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (!in.isReadable()) return;

        int dataLength = VarInts.readVarInt(in);
        if (dataLength == 0) {

            out.add(in.readRetainedSlice(in.readableBytes()));
            return;
        }
        if (dataLength < threshold) {
            throw new DecoderException("Paquet compresse sous le seuil (" + dataLength + ")");
        }
        if (dataLength > ProtocolConstants.MAX_PACKET_SIZE) {
            throw new DecoderException("Paquet decompresse trop grand (" + dataLength + ")");
        }

        byte[] compressed = new byte[in.readableBytes()];
        in.readBytes(compressed);
        inflater.setInput(compressed);

        byte[] decompressed = new byte[dataLength];
        try {
            int produced = inflater.inflate(decompressed);
            if (produced != dataLength) {
                throw new DecoderException(
                        "Taille inflatee incoherente : " + produced + " != " + dataLength);
            }
            out.add(ctx.alloc().buffer(dataLength).writeBytes(decompressed));
        } catch (java.util.zip.DataFormatException e) {
            throw new DecoderException("Flux zlib invalide", e);
        } finally {
            inflater.reset();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        inflater.end();
    }
}

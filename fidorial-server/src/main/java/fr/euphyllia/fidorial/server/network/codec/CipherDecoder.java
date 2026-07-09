package fr.euphyllia.fidorial.server.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import javax.crypto.Cipher;
import java.util.List;

public final class CipherDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Cipher cipher;

    public CipherDecoder(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte[] input = new byte[msg.readableBytes()];
        msg.readBytes(input);
        out.add(ctx.alloc().buffer().writeBytes(cipher.update(input)));
    }
}

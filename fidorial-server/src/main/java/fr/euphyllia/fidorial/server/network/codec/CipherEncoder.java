package fr.euphyllia.fidorial.server.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.crypto.Cipher;

public final class CipherEncoder extends MessageToByteEncoder<ByteBuf> {

    private final Cipher cipher;

    public CipherEncoder(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        byte[] input = new byte[msg.readableBytes()];
        msg.readBytes(input);
        out.writeBytes(cipher.update(input));
    }
}

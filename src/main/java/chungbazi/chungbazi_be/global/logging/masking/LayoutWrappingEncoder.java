package chungbazi.chungbazi_be.global.logging.masking;

import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.EncoderBase;

import java.nio.charset.Charset;

public class LayoutWrappingEncoder<E> extends EncoderBase<E> {

    protected Layout<E> layout;
    private Charset charset;

    public void setLayout(Layout<E> layout) {
        this.layout = layout;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    public byte[] encode(E e) {
        if (layout == null) {
            return null;
        }
        String text = layout.doLayout(e);
        return convertToBytes(text);
    }

    @Override
    public byte[] footerBytes() {
        return new byte[0];
    }

    private byte[] convertToBytes(String text) {
        if (charset == null) {
            return text.getBytes();
        } else {
            return text.getBytes(charset);
        }
    }
}

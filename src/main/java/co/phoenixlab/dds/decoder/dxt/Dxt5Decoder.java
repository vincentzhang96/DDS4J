package co.phoenixlab.dds.decoder.dxt;

import co.phoenixlab.dds.Dds;

public class Dxt5Decoder extends AbstractDxtDecoder {

    public Dxt5Decoder(Dds dds) {
        super(dds, BcHelper.BC3_DXT5_BLOCK_SIZE_BYTES);
    }

    @Override
    public int[] decodeLine() {
        return null;
    }
}

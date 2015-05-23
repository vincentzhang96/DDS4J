package co.phoenixlab.dds.decoder;

import co.phoenixlab.dds.Dds;

public class RGBDecoder extends AbstractBasicDecoder {

    public RGBDecoder(Dds dds) {
        super(dds);
    }

    @Override
    public int[] decodeLine() {
        throw new UnsupportedOperationException();
    }
}

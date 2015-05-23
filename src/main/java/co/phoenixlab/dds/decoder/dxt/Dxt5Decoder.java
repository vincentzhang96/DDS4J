package co.phoenixlab.dds.decoder.dxt;

import co.phoenixlab.dds.Dds;
import co.phoenixlab.dds.decoder.FormatDecoder;

import java.util.Iterator;
import java.util.stream.Stream;

public class Dxt5Decoder implements FormatDecoder {

    private final Dds dds;

    public Dxt5Decoder(Dds dds) {
        this.dds = dds;
    }

    @Override
    public int[] decodeLine() {
        return new int[0];
    }

    @Override
    public Iterator<int[]> lineIterator() {
        return null;
    }

    @Override
    public Stream<int[]> lineStream() {
        return null;
    }
}

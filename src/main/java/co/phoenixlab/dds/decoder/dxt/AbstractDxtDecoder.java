package co.phoenixlab.dds.decoder.dxt;

import co.phoenixlab.dds.Dds;
import co.phoenixlab.dds.DdsHeader;
import co.phoenixlab.dds.decoder.FormatDecoder;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class AbstractDxtDecoder implements FormatDecoder {

    protected Dds dds;
    protected int currLine;
    protected final int numLines;
    protected final int lineWidth;

    public AbstractDxtDecoder(Dds dds) {
        this.dds = dds;
        this.currLine = 0;
        DdsHeader header = dds.getHeader();
        this.numLines = header.getDwHeight();
        this.lineWidth = header.getDwWidth();
    }

    public Stream<int[]> lineStream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public Spliterator<int[]> spliterator() {
        return Spliterators.spliterator(lineIterator(), numLines,
                Spliterator.SIZED | Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED);
    }

    public Iterator<int[]> lineIterator() {
        return new LineIterator();
    }

    class LineIterator implements Iterator<int[]> {
        public boolean hasNext() {
            return currLine < numLines;
        }

        public int[] next() {
            return decodeLine();
        }
    }
}

package co.phoenixlab.dds;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ReadableByteChannel;

import static co.phoenixlab.dds.InternalUtils.verifyThat;
import static co.phoenixlab.dds.InternalUtils.verifyThatNot;
import static java.lang.Integer.reverseBytes;
import static java.lang.Integer.toHexString;

public class Dds implements DdsReadable {

    public static final int DW_MAGIC = reverseBytes(0x44445320);    //  'D' 'D' 'S' ' '

    private int dwMagic;
    private DdsHeader header;
    private DdsHeaderDxt10 header10;
    private byte[] bdata;
    private byte[] bdata2;

    public Dds() {
    }

    public int getDwMagic() {
        return dwMagic;
    }

    public DdsHeader getHeader() {
        return header;
    }

    public DdsHeaderDxt10 getHeader10() {
        return header10;
    }

    public byte[] getBdata() {
        return bdata;
    }

    public byte[] getBdata2() {
        return bdata2;
    }

    @Override
    public void validate() throws InvalidDdsException {
        verifyThat(dwMagic, i -> i == DW_MAGIC, "Invalid DDS: dwMagic not 'DDS '");
        verifyThatNot(header, h -> h == null, "Invalid DDS: No header");
        header.validate();
        verifyThat(header10, h -> header.getDdspf().isDx10HeaderPresent() == (h != null), "Invalid DDS: " +
                "DDSPF indicates presence of DX10Header, but no DX10Header is present");
        if (header10 != null) {
            header10.validate();
        }
    }

    @Override
    public void read(DataInputStream inputStream) throws IOException {
        dwMagic = reverseBytes(inputStream.readInt());
        header = new DdsHeader();
        header.read(inputStream);
        if (header.getDdspf().isDx10HeaderPresent()) {
            header10 = new DdsHeaderDxt10();
            header10.read(inputStream);
        }
        if (header.getDwFlags().contains(DdsHeader.Flags.DDSD_LINEARSIZE)) {
            bdata = new byte[header.getDwPitchOrLinearSize()];
            inputStream.readFully(bdata);
        } else {
            //TODO
            System.err.println("UNSUPPORTED " + header.getDwFlags().toString());
        }
        validate();
    }

    @Override
    public void read(ReadableByteChannel byteChannel) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(4);
        int read;
        //noinspection StatementWithEmptyBody
        while ((read = byteChannel.read(buf)) > 0);
        if (read < 0) {
            throw new EOFException();
        }
        buf.flip();
        buf.order(ByteOrder.LITTLE_ENDIAN);
        dwMagic = buf.getInt();
        header = new DdsHeader();
        header.read(byteChannel);
        if (header.getDdspf().isDx10HeaderPresent()) {
            header10 = new DdsHeaderDxt10();
            header10.read(byteChannel);
        }
        if (header.getDwFlags().contains(DdsHeader.Flags.DDSD_LINEARSIZE)) {
            buf = ByteBuffer.allocate(header.getDwPitchOrLinearSize());
            bdata = new byte[header.getDwPitchOrLinearSize()];
            //noinspection StatementWithEmptyBody
            while ((read = byteChannel.read(buf)) > 0);
            if (read < 0) {
                throw new EOFException();
            }
            buf.flip();
            buf.get(bdata);
        } else {
            //TODO
            System.err.println("UNSUPPORTED " + header.getDwFlags().toString());
        }
        validate();
    }

    @Override
    public void read(ByteBuffer buf) throws InvalidDdsException {
        dwMagic = buf.getInt();
        header = new DdsHeader();
        header.read(buf);
        if (header.getDdspf().isDx10HeaderPresent()) {
            header10 = new DdsHeaderDxt10();
            header10.read(buf);
        }
        if (header.getDwFlags().contains(DdsHeader.Flags.DDSD_LINEARSIZE)) {
            bdata = new byte[header.getDwPitchOrLinearSize()];
            buf.get(bdata);
        } else {
            //TODO
            System.err.println("UNSUPPORTED " + header.getDwFlags().toString());
        }
        validate();
    }

    @Override
    public String toString() {
        return "Dds{" +
                "dwMagic=" + toHexString(dwMagic) +
                ", header=" + header +
                ", header10=" + (header10 == null ? "N/A" : header10) +
                ", bdataSize=" + (bdata == null ? "N/A" : bdata.length) +
                ", bdata2Size=" + (bdata2 == null ? "N/A" : bdata2.length) +
                '}';
    }
}

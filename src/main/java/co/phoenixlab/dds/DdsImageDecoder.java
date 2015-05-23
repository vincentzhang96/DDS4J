package co.phoenixlab.dds;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DdsImageDecoder {

    public DdsImageDecoder() {

    }

    public byte[] convertToPNG(Dds dds) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            convertToPNG(dds, byteArrayOutputStream);
        } catch (IOException e) {
            //  Impossible
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void convertToPNG(Dds dds, OutputStream outputStream) throws IOException {
        DdsHeader header = dds.getHeader();


        ImageInfo imageInfo = new ImageInfo(header.getDwWidth(), header.getDwHeight(), 8, true);
        PngWriter pngWriter = new PngWriter(outputStream, imageInfo);
        ImageLineInt imageLine = new ImageLineInt(imageInfo);
    }



}

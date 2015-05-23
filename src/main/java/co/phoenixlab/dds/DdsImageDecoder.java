package co.phoenixlab.dds;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import co.phoenixlab.dds.decoder.RGBADecoder;

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
        //  TEMP - replace with decoder selector
        RGBADecoder decoder = new RGBADecoder(dds);
        
        ImageInfo imageInfo = new ImageInfo(header.getDwWidth(), header.getDwHeight(), 8, true);
        PngWriter pngWriter = new PngWriter(outputStream, imageInfo);
        ImageLineInt imageLine = new ImageLineInt(imageInfo);
        for (int[] ints : decoder) {
            for (int i = 0; i < ints.length; i++) {
                ImageLineHelper.setPixelRGBA8(imageLine, i, ints[i]);
            }
            pngWriter.writeRow(imageLine);
        }
        pngWriter.end();
    }


}

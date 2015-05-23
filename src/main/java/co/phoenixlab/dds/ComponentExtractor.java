package co.phoenixlab.dds;

public class ComponentExtractor {

    private final DdsPixelFormat format;
    private final byte[] data;

    public ComponentExtractor(DdsPixelFormat format, byte[] data) {
        this.format = format;
        this.data = data;
    }


}

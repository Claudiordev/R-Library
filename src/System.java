import java.io.File;

public class System {

    /**
     * @return long value with total of free bytes within system path "/"
     * <p>The returned number of unallocated bytes is a hint, but not
     * a guarantee, that it is possible to use most or any of these
     *  bytes.</p>
     */
    public static long getSystemFreeSpace() {
        return (new File("/")).getFreeSpace();
    }

    /**
     * @return long value with total of space in bytes, within system path "/"
     */
    public static long getSystemTotalSpace() {
        return (new File("/")).getTotalSpace();
    }

    /**
     * @return long value with total of usable free bytes within system path "/"
     */
    public static long getSystemUsableSpace() {
        return (new File("/")).getUsableSpace();
    }
}

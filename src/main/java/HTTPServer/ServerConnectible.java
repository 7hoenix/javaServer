package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface ServerConnectible extends Closeable {
    Connectible accept() throws IOException;
    void close();
}
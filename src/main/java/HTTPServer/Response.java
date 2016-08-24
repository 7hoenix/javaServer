package HTTPServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Response {
    public byte[] wrapContentType(byte[] response, File currentFile) throws IOException {
        String contentType = findContentType(currentFile);
        if (contentType != null) {
            String command = "Content-Type: " + contentType + "\r\n";
            return combine(response, command.getBytes());
        } else {
            return response;
        }
    }

    public byte[] wrapContentLength(byte[] response, File currentFile) throws IOException {
        int sizeOfFile = findSizeOfFile(currentFile);
        if (sizeOfFile > 0) {
            String command = "Content-Length: " + new String().valueOf(sizeOfFile) + "\r\n";
            return combine(response, command.getBytes());
        } else {
            return response;
        }
    }

    private int findSizeOfFile(File currentFile) throws IOException {
        if (currentFile.isFile()) {
            byte[] fileInBytes = Files.readAllBytes(Paths.get(currentFile.getPath()));
            return fileInBytes.length;
        } else {
            return 0;
        }
    }

    private byte[] combine(byte[] response, byte[] addend) throws IOException {
        ByteArrayOutputStream combined = new ByteArrayOutputStream();
        combined.write(response);
        combined.write(addend);
        return combined.toByteArray();
    }

    private String findContentType(File currentFile) {
        String path = currentFile.getPath();
        String contentType = null;
        if (path.indexOf(".") >= 0) {
            int finalPeriod = path.lastIndexOf(".");
            String extensionType = path.substring(finalPeriod + 1);
            if (supportedTypes().get(extensionType) != null) {
                contentType = (String) supportedTypes().get(extensionType);
            } else {
                contentType = "text/html";
            }
        }
        return contentType;
    }

    private Map supportedTypes() {
        HashMap supportedTypes = new HashMap();
        supportedTypes.put("jpg", "image/jpeg");
        supportedTypes.put("jpeg", "image/jpeg");
        supportedTypes.put("gif", "image/gif");
        supportedTypes.put("png", "image/png");
        supportedTypes.put("txt", "text/plain");
        return supportedTypes;
    }
}

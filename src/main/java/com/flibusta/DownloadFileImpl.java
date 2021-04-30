package com.flibusta;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
public class DownloadFileImpl implements IDownloadFile {

    public File downloadFileTo(String linkToDownload) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9150));
        URL sourceUrl = new URL(linkToDownload);
        HttpURLConnection connection = (HttpURLConnection) sourceUrl.openConnection(proxy);
        connection.connect();

        final String nameFile = connection.getHeaderField("Content-Disposition")
                .replaceAll("attachment; filename=", "")
                .replaceAll("\"", "");

        File targetFile = new File("F:/test/" + nameFile);

        if (targetFile.exists()) {
            connection.disconnect();
            return targetFile;
        }

        ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream("F:/test/" + nameFile);
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();
        connection.disconnect();
        return targetFile;
    }
}

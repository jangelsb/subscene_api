package subscene.api.utils;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class UrlUtil {
    private static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";

   public static Document getDocument(String url) throws IOException{
       return Jsoup.connect(url).userAgent(userAgent).get();
    }

    //    http://stackoverflow.com/questions/4797593/java-io-ioexception-server-returned-http-response-code-403-for-url
    public static void downloadFile(String url, String outputFileName) throws IOException {

        String fileName = outputFileName; //The file that will be saved on your computer
        URL link = new URL(url); //The file that you want to download
        URLConnection uc;
        uc = link.openConnection();
        uc.addRequestProperty("User-Agent", userAgent);
        uc.connect();
        uc.getInputStream();
        //Code to download
        InputStream in = new BufferedInputStream(uc.getInputStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(response);
        fos.close();
        //End download code
    }
}

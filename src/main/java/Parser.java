import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

public class Parser {


    public static void main(String[] args) throws IOException {
        List<Book> bookList = new ArrayList<>();
        HttpGetDownloadFileExample example = new HttpGetDownloadFileExample();

        Document document = Jsoup.connect("http://avalanche.vip/deepworlds.htm").get();
        Elements listBooks = document.getElementsByAttributeValue("class", "item");

        for (Element e: listBooks) {
            String url1 = e.child(0).attr("href");
            String bookName = e.child(0).attr("title");

            bookList.add(new Book(url1, bookName));
 //           example.downloadFile(url1,bookName);

        }

        System.out.println(bookList);



    }

    public static class HttpGetDownloadFileExample {

        private ReadableByteChannel readableChannelForHttpResponseBody = null;
        private FileChannel fileChannelForDownloadedFile = null;

        public void downloadFile(String url, String file) throws IOException {
            try {
                // Define server endpoint
                URL robotsUrl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) robotsUrl.openConnection();

                // Get a readable channel from url connection
                readableChannelForHttpResponseBody = Channels.newChannel(urlConnection.getInputStream());

                // Create the file channel to save file
                FileOutputStream fosForDownloadedFile = new FileOutputStream(file);
                fileChannelForDownloadedFile = fosForDownloadedFile.getChannel();

                // Save the body of the HTTP response to local file
                fileChannelForDownloadedFile.transferFrom(readableChannelForHttpResponseBody, 0, Long.MAX_VALUE);

            } catch (IOException ioException) {
                System.out.println("IOException occurred while contacting server.");
            } finally {

                if (readableChannelForHttpResponseBody != null) {

                    try {
                        readableChannelForHttpResponseBody.close();
                    } catch (IOException ioe) {
                        System.out.println("Error while closing response body channel");
                    }
                }

                if (fileChannelForDownloadedFile != null) {

                    try {
                        fileChannelForDownloadedFile.close();
                    } catch (IOException ioe) {
                        System.out.println("Error while closing file channel for downloaded file");
                    }
                }

            }
        }
    }

    static class Book {
        private final String url;
        private final String name;

    public Book (String url, String name) {
        this.url = url;
        this.name = name;
    }
/*
    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }
*/
        @Override
        public String toString() {
            return "Book{" +
                    "url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

}

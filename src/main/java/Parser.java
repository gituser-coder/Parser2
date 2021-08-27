import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Parser {


    public static void main(String[] args) throws IOException {
        List<Book> bookList = new ArrayList<>();

        Document document = Jsoup.connect("http://avalanche.vip/deepworlds.htm").get();
        Elements listBooks = document.getElementsByAttributeValue("class", "item");

        for (Element e: listBooks) {
            String url1 = e.child(0).attr("href");
            String bookName = e.child(0).attr("title");

            bookList.add(new Book(url1, bookName));
        }

        System.out.println(bookList);

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

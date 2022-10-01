import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {
    private static Document request(String url, ArrayList<String> v) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                System.out.println("Link: " + url);
                System.out.println(doc.title());
                v.add(url);

                return doc;
            }

            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static void crawl(int level, String url, ArrayList<String> visited) {
        if (level <= 5) {
            Document doc = request(url, visited);

            if (doc != null) {
                for (Element link : doc.select(("a[href]"))) {
                    String next_link = link.absUrl("href");
                    if (visited.contains(next_link) == false) {
                        crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your link: ");
        String url = sc.nextLine();
        sc.close();

        crawl(1, url, new ArrayList<String>());
    }
}

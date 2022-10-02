import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void print(Boolean ln, String msg, Object... args) {
        if (!ln) System.out.print(String.format(msg, args));
        else System.out.println(String.format(msg, args));
    }

    public static String enterURL() {
        print(false, "Enter your link: ");
        return sc.nextLine();
    }

    public static Document fetchFromURL(String url) throws IOException {
        print(true, "Fetching %s...", url);
        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "";
        url = enterURL();

        Crawler crw = new Crawler(Objects.requireNonNull(fetchFromURL(url)));
        crw.getAllData();
    }
}

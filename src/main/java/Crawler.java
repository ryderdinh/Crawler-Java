import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Crawler {
    private String title = "";
    private Document doc;
    private ArrayList<String> imageLinks = new ArrayList<>();
    private ArrayList<String> importLinks = new ArrayList<>();
    private ArrayList<String> moreLinks = new ArrayList<>();
    private ArrayList<String> aTagLinks = new ArrayList<>();


    public static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }

    public Crawler(Document doc) {
        this.title = doc.title();
        this.doc = doc;
    }

    public String getTitle() {
        return title;
    }

    public Document getDoc() {
        return doc;
    }

    public void getMedia() {
        Elements media = doc.select("[src]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.normalName().equals("img")) {
                imageLinks.add(src.attr("abs:src"));
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            } else {
                moreLinks.add(src.attr("abs:src"));
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }
        }
    }

    public void getImports() {
        Elements imports = doc.select("link[href]");

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            importLinks.add(link.attr("abs:href"));
            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
        }
    }

    public void getLinks() {
        Elements links = doc.select("a[href]");

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            aTagLinks.add(link.attr("abs:href"));
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    public void getAllData() {
        print("Title: " + getTitle());
        getMedia();
        getImports();
        getLinks();
    }
}

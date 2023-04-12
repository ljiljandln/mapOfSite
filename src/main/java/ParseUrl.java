import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;

public class ParseUrl {

    private final String url;
    private final String tab;
    private final TreeSet<ParseUrl> parseUrlTreeSet = new TreeSet<>(Comparator.comparing(o -> o.url));

    public ParseUrl(String url, String tab) {
        this.url = url;
        this.tab = tab;
    }

    public String getTab() {
        return tab;
    }

    public String getUrl() {
        return url;
    }

    public TreeSet<ParseUrl> getParseUrlTreeSet() {
        return parseUrlTreeSet;
    }

    public List<String> getUrls() {
        List<String> urls = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements link = doc.select("a");
            link.forEach(e -> {
                String href = e.absUrl("href");
                if (href.contains(url)) {
                    urls.add(href);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return urls;
    }
}

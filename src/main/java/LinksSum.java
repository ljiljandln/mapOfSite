import java.util.*;
import java.util.concurrent.RecursiveTask;

public class LinksSum extends RecursiveTask<ParseUrl> {

    private final ParseUrl parseUrl;
    private Set<String> allUrls;

    public LinksSum(ParseUrl parseUrl) {
        this.parseUrl = parseUrl;
        allUrls = Collections.synchronizedSet(new HashSet<>());
    }

    public LinksSum(ParseUrl parseUrl, Set<String> allUrls) {
        this.parseUrl = parseUrl;
        this.allUrls = allUrls;
    }

    @Override
    protected ParseUrl compute() {
        List<LinksSum> taskList = new ArrayList<>();

        for (String link : parseUrl.getUrls()) {
            if (allUrls.contains(link)) {
                continue;
            }
            allUrls.add(link);
            LinksSum task = new LinksSum(new ParseUrl(link, parseUrl.getTab() + "\t"), allUrls);
            task.fork();
            taskList.add(task);
        }

        for (LinksSum linksSum : taskList) {
            parseUrl.getParseUrlTreeSet().add(linksSum.join());
        }

        return parseUrl;
    }
}

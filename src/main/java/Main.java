import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {
        ParseUrl root = new ParseUrl("https://lenta.ru/", "");
        ParseUrl parseUrl = new ForkJoinPool().invoke(new LinksSum(root));

        FileWriter writer = getWriter();
        print(parseUrl, writer);

        writer.flush();
        writer.close();
    }

    public static FileWriter getWriter() throws IOException {
        String separator = File.separator;
        String strPath = "src" + separator + "main" + separator + "java" + separator + "Links.txt";
        Path path = Path.of(strPath);
        File file = new File(path.toUri());
        return new FileWriter(file, false);
    }

    public static void print(ParseUrl parseUrl, FileWriter writer) throws IOException {
        StringBuffer sb = new StringBuffer();
        sb.append(parseUrl.getTab()).append(parseUrl.getUrl()).append("\n");

        writer.write(sb.toString());
        for (ParseUrl parseUrl1 : parseUrl.getParseUrlTreeSet()) {
            print(parseUrl1, writer);
        }
    }
}
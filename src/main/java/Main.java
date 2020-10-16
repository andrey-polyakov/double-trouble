import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Main class of the application.
 */
public class Main {

    private static final List<String> samplePatterns = List.of(
            "James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph",
            "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth",
            "Steven", "Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy",
            "Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond",
            "Gregory", "Joshua", "Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas",
            "Henry", "Carl", "Arthur", "Ryan", "Roger");

    private static final String sampleFileResource = "/files/big.txt";

    private static final int sampleBlockSize = 1000;

    private final List<String> patterns;

    private final String fileResource;

    private final int blockSize;

    // sample run
    public Main() {
        this(samplePatterns, sampleFileResource, sampleBlockSize);
    }

    /**
     * Constructor for Main class.
     *
     * @param patterns     list of patterns
     * @param fileResource file location
     * @param blockSize    lines block size
     */
    public Main(final List<String> patterns, final String fileResource, final int blockSize) {
        this.patterns = patterns;
        this.fileResource = fileResource;
        this.blockSize = blockSize;
    }

    /**
     * Searches for patterns in a file text
     *
     * @return pattern search result for a file text
     */
    public TextSearchResult process() {
        final URI filePathURI;
        try {
            filePathURI = Matcher.class.getResource(fileResource).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to read file from resource:'%s'.", fileResource), e);
        }
        final Path filePath = Path.of(filePathURI);
        final long linesNumberTotally = Util.getLinesNumber(filePath);
        final long blocksNumber = (linesNumberTotally + blockSize - 1) / blockSize;
        final Matcher matcher = new Matcher(patterns);

        return LongStream
                .range(0, blocksNumber)
                .boxed()
                .parallel()
                .map(blockNumber -> {
                    final long startLine = blockNumber * blockSize;
                    return matcher.search(startLine, Util.getLines(filePath, startLine, blockSize));
                })
                .reduce(BlockSearchResult.empty(), new Aggregator())
                .convertToTextSearchResult();
    }

    // sample run
    public static void main(String[] args) {

        final Main main = new Main();
        final TextSearchResult process = main.process();
        process.print();
    }
}

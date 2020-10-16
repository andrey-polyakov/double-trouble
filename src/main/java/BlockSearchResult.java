import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Pattern search results for a block of lines
 */
public final class BlockSearchResult {

    //Map<pattern, SortedMap<line number, list of block positions>>
    private final Map<String, SortedMap<Long, List<Integer>>> patternPositionsMap;

    public BlockSearchResult(final Map<String, SortedMap<Long, List<Integer>>> patternPositionsMap) {
        this.patternPositionsMap = patternPositionsMap;
    }

    /**
     * Generates an empty block search result
     *
     * @return the empty block result
     */
    public static BlockSearchResult empty() {
        return new BlockSearchResult(new HashMap<>());
    }

    /**
     * Generates an entry set from a pattern positions map
     *
     * @return the entry set
     */
    public Set<Map.Entry<String, SortedMap<Long, List<Integer>>>> entrySet() {
        return patternPositionsMap.entrySet();
    }

    /**
     * Generates pattern search result for a text
     *
     * @return pattern search result for a text
     */
    public TextSearchResult convertToTextSearchResult() {
        return new TextSearchResult(new TreeMap<>(patternPositionsMap));
    }

}

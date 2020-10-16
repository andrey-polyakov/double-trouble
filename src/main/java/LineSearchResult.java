import java.util.List;
import java.util.Map;

/**
 * Pattern search results for a line
 */
public final class LineSearchResult {

    // Map<pattern, list of block positions>
    private final Map<String, List<Integer>> patternPositionsMap;

    public LineSearchResult(final Map<String, List<Integer>> patternPositionsMap) {
        this.patternPositionsMap = patternPositionsMap;
    }

    /**
     * Returns pattern positions map
     *
     * @return pattern positions map
     */
    public Map<String, List<Integer>> getPatternPositionsMap() {
        return patternPositionsMap;
    }

}

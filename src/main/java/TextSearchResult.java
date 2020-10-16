import java.util.List;
import java.util.SortedMap;

/**
 * Pattern search results for a text
 */
public final class TextSearchResult {

    //SortedMap<pattern, SortedMap<line number, list of block positions>>
    private final SortedMap<String, SortedMap<Long, List<Integer>>> patternPositionsMap;

    public TextSearchResult(final SortedMap<String, SortedMap<Long, List<Integer>>> patternPositionsMap) {
        this.patternPositionsMap = patternPositionsMap;
    }

    public SortedMap<Long, List<Integer>> getResultsForPattern(final String pattern){
        return patternPositionsMap.get(pattern);
    }

    /**
     * Prints results to console
     */
    public void print() {
        patternPositionsMap.forEach((pattern, positionsMapPerLine) -> positionsMapPerLine.forEach(
                (lineNumber, positions) ->
                        positions
                                .stream()
                                .sorted()
                                .forEach(blockPosition -> System.out.printf("Pattern: '%s', lineOffSet: %d, charOffSet: %d.%n", pattern, lineNumber, blockPosition))
        ));
    }

}

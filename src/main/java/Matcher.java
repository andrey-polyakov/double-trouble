import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * Matches patterns against a text.
 */
public final class Matcher {

    private static final int R = 256;

    private final List<String> patterns;

    private final Map<Long, List<String>> patternHashesMap;

    private final int minLength;

    private final long q;

    private final long RM;

    /**
     * Constructor for Matcher.
     *
     * @param patterns the list of patterns
     */
    public Matcher(final List<String> patterns) {
        this.patterns = patterns;
        minLength = patterns.stream().mapToInt(String::length).min().orElse(0);
        q = Util.longRandomPrime();
        long rM = 1;
        for (int i = 1; i <= minLength - 1; i++) {
            rM = (R * rM) % q;
        }
        RM = rM;
        patternHashesMap = patterns.stream().collect(groupingBy(pattern -> hash(pattern, minLength),
                mapping(Function.identity(), toList())
        ));
    }

    /**
     * Searches for pattern positions in a block of lines
     *
     * @param startLine the first line number
     * @param lines     stream of lines
     * @return          block search results
     */
    public BlockSearchResult search(final long startLine, final Stream<String> lines) {

        final Map<String, SortedMap<Long, List<Integer>>> result = new HashMap<>();
        patterns.forEach(pattern -> result.put(pattern, new TreeMap<>()));
        lines
                .forEachOrdered(new Consumer<>() {
                    int counter = 0;
                    int blockPosition = 0;

                    public void accept(final String line) {
                        counter++;
                        final long lineNumber = startLine + counter;
                        final LineSearchResult searchResult = search(line, blockPosition);
                        searchResult.getPatternPositionsMap().forEach((pattern, positions) -> result.get(pattern).put(lineNumber, positions));
                        blockPosition += line.length();
                    }
                });

        return new BlockSearchResult(result);
    }

    /**
     * Searches for pattern positions in a line
     *
     * @param text          the text
     * @param blockPosition the block position
     * @return              line search results
     */
    private LineSearchResult search(final String text, final int blockPosition) {
        final Map<String, List<Integer>> result = new HashMap<>();
        final int n = text.length();
        if (n < minLength) {
            return new LineSearchResult(result);
        }
        long txtHash = hash(text, minLength);

        final long initTxtHash = txtHash;

        if (patternHashesMap.containsKey(initTxtHash)) {

            patternHashesMap.get(initTxtHash).forEach(pattern -> {
                        if (match(pattern, text, 0)) {
                            final List<Integer> positions = new ArrayList<>();
                            positions.add(blockPosition);
                            result.put(pattern, positions);
                        }
                    }
            );
        }

        for (int i = minLength; i < n; i++) {

            // rolling hash
            txtHash = (txtHash + q - RM * text.charAt(i - minLength) % q) % q;
            txtHash = (txtHash * R + text.charAt(i)) % q;

            final int offset = i - minLength + 1;
            final long currentTxtHash = txtHash;
            if (patternHashesMap.containsKey(currentTxtHash)) {
                patternHashesMap.get(currentTxtHash).forEach(pattern -> {
                    if (match(pattern, text, offset)) {
                        result.putIfAbsent(pattern, new ArrayList<>());
                        final List<Integer> positions = result.get(pattern);
                        positions.add(blockPosition + offset);
                    }
                });
            }
        }

        return new LineSearchResult(result);
    }

    /**
     * Calculates hash of a text for the length of m.
     *
     * @param text the text
     * @param m    the length
     * @return     hash value
     */
    private long hash(final String text, final int m) {
        long h = 0;
        for (int j = 0; j < m; j++) {
            h = (R * h + text.charAt(j)) % q;
        }
        return h;
    }

    /**
     * Matches a pattern against a text at a defined position.
     *
     * @param pattern       the pattern
     * @param text          the text
     * @param startPosition the start position
     * @return              true - found a match, false - no match found
     */
    private boolean match(final String pattern, final String text, final int startPosition) {
        if (text.length() < startPosition + pattern.length()) {
            return false;
        }

        // we don't want substrings
        if (text.length() >= startPosition + pattern.length() + 1 && Character.isLetter(text.charAt(startPosition + pattern.length()))) {
            return false;
        }

        if (startPosition >= 1 && Character.isLetter(text.charAt(startPosition - 1))) {
            return false;
        }

        for (int j = 0; j < pattern.length(); j++) {
            if (pattern.charAt(j) != text.charAt(startPosition + j))
                return false;
        }

        return true;
    }

}

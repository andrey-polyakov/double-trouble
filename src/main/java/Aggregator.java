import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Combines pattern search results of two blocks.
 */
public final class Aggregator implements BinaryOperator<BlockSearchResult> {

    @Override
    public BlockSearchResult apply(final BlockSearchResult blockSearchResult, final BlockSearchResult blockSearchResult2) {

        final Stream<Map.Entry<String, SortedMap<Long, List<Integer>>>> combined = Stream.concat(blockSearchResult.entrySet().stream(),
                blockSearchResult2.entrySet().stream());
        return new BlockSearchResult(combined.collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (m1, m2) ->
                                Stream
                                        .concat(m1.entrySet().stream(), m2.entrySet().stream())
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                (positionsList1, positionsList12) -> {
                                                    throw new RuntimeException("Positions for duplicate lines are not allowed.");
                                                }, TreeMap::new))
                )));
    }

}

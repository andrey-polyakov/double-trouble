import org.junit.Test;

import java.util.List;
import java.util.SortedMap;

import static org.junit.Assert.assertEquals;

/**
 * Tests text search results
 */
public class TestMain {

    @Test
    public void testTotalNumberOfOccurrences() {
        final Main main = new Main();
        final TextSearchResult textSearchResult = main.process();
        assertEquals(83, numberOfResults(textSearchResult, "James"));
        assertEquals(143, numberOfResults(textSearchResult, "John"));
        assertEquals(23, numberOfResults(textSearchResult, "Robert"));
        assertEquals(68, numberOfResults(textSearchResult, "Michael"));
        assertEquals(60, numberOfResults(textSearchResult, "William"));
        assertEquals(9, numberOfResults(textSearchResult, "David"));
        assertEquals(5, numberOfResults(textSearchResult, "Richard"));
        assertEquals(38, numberOfResults(textSearchResult, "Charles"));
        assertEquals(41, numberOfResults(textSearchResult, "Joseph"));
        assertEquals(2, numberOfResults(textSearchResult, "Christopher"));
        assertEquals(55, numberOfResults(textSearchResult, "Daniel"));
        assertEquals(15, numberOfResults(textSearchResult, "Paul"));
        assertEquals(4, numberOfResults(textSearchResult, "Mark"));
        assertEquals(4, numberOfResults(textSearchResult, "Donald"));
        assertEquals(143, numberOfResults(textSearchResult, "George"));
        assertEquals(1, numberOfResults(textSearchResult, "Kenneth"));
        assertEquals(0, numberOfResults(textSearchResult, "Steven"));
        assertEquals(6, numberOfResults(textSearchResult, "Edward"));
        assertEquals(0, numberOfResults(textSearchResult, "Brian"));
        assertEquals(0, numberOfResults(textSearchResult, "Ronald"));
        assertEquals(6, numberOfResults(textSearchResult, "Anthony"));
        assertEquals(0, numberOfResults(textSearchResult, "Kevin"));
        assertEquals(1, numberOfResults(textSearchResult, "Jason"));
        assertEquals(3, numberOfResults(textSearchResult, "Matthew"));
        assertEquals(0, numberOfResults(textSearchResult, "Gary"));
        assertEquals(2, numberOfResults(textSearchResult, "Timothy"));
        assertEquals(1, numberOfResults(textSearchResult, "Jose"));
        assertEquals(0, numberOfResults(textSearchResult, "Larry"));
        assertEquals(0, numberOfResults(textSearchResult, "Jeffrey"));
        assertEquals(19, numberOfResults(textSearchResult, "Frank"));
        assertEquals(23, numberOfResults(textSearchResult, "Scott"));
        assertEquals(0, numberOfResults(textSearchResult, "Eric"));
        assertEquals(5, numberOfResults(textSearchResult, "Stephen"));
        assertEquals(1167, numberOfResults(textSearchResult, "Andrew"));
        assertEquals(0, numberOfResults(textSearchResult, "Raymond"));
        assertEquals(2, numberOfResults(textSearchResult, "Gregory"));
        assertEquals(1, numberOfResults(textSearchResult, "Joshua"));
        assertEquals(0, numberOfResults(textSearchResult, "Jerry"));
        assertEquals(0, numberOfResults(textSearchResult, "Dennis"));
        assertEquals(1, numberOfResults(textSearchResult, "Walter"));
        assertEquals(8, numberOfResults(textSearchResult, "Patrick"));
        assertEquals(51, numberOfResults(textSearchResult, "Peter"));
        assertEquals(0, numberOfResults(textSearchResult, "Harold"));
        assertEquals(28, numberOfResults(textSearchResult, "Douglas"));
        assertEquals(47, numberOfResults(textSearchResult, "Henry"));
        assertEquals(2, numberOfResults(textSearchResult, "Carl"));
        assertEquals(32, numberOfResults(textSearchResult, "Arthur"));
        assertEquals(0, numberOfResults(textSearchResult, "Ryan"));
        assertEquals(5, numberOfResults(textSearchResult, "Roger"));
    }

    @Test
    public void testRobert() {
        final Main main = new Main();
        final TextSearchResult textSearchResult = main.process();
        final SortedMap<Long, List<Integer>> robertSearchResults = textSearchResult.getResultsForPattern("Robert");
        assertEquals(98727, (int) robertSearchResults.get(3893L).get(0));
        assertEquals(99996, (int) robertSearchResults.get(3899L).get(0));
        assertEquals(103028, (int) robertSearchResults.get(3929L).get(0));
        assertEquals(106485, (int) robertSearchResults.get(3943L).get(0));
        assertEquals(15998, (int) robertSearchResults.get(4251L).get(0));
        assertEquals(22482, (int) robertSearchResults.get(4271L).get(0));
        assertEquals(13998, (int) robertSearchResults.get(7270L).get(0));
        assertEquals(6041, (int) robertSearchResults.get(8103L).get(0));
        assertEquals(25992, (int) robertSearchResults.get(8473L).get(0));
        assertEquals(7249, (int) robertSearchResults.get(10123L).get(0));
        assertEquals(42618, (int) robertSearchResults.get(10777L).get(0));
        assertEquals(43191, (int) robertSearchResults.get(10785L).get(0));
        assertEquals(18615, (int) robertSearchResults.get(11361L).get(0));
        assertEquals(38125, (int) robertSearchResults.get(13687L).get(0));
        assertEquals(31555, (int) robertSearchResults.get(15522L).get(0));
        assertEquals(42748, (int) robertSearchResults.get(16751L).get(0));
        assertEquals(50866, (int) robertSearchResults.get(17891L).get(0));
        assertEquals(38966, (int) robertSearchResults.get(27867L).get(0));
        assertEquals(7054, (int) robertSearchResults.get(28509L).get(0));
        assertEquals(8201, (int) robertSearchResults.get(28607L).get(0));
        assertEquals(12006, (int) robertSearchResults.get(28904L).get(0));
        assertEquals(5528, (int) robertSearchResults.get(29409L).get(0));
        assertEquals(37011, (int) robertSearchResults.get(39669L).get(0));
    }

    /**
     * Calculates total number of found patterns
     *
     * @param textSearchResult patterns search results for a file
     * @param pattern          the pattern
     * @return total number of found patterns
     */
    private static int numberOfResults(final TextSearchResult textSearchResult, final String pattern) {
        return textSearchResult.getResultsForPattern(pattern).values().stream().mapToInt(List::size).sum();
    }

}

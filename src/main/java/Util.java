import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Utility class.
 */
public final class Util {

    // we don't want to allow creation instances of this class.
    private Util() {

    }

    /**
     * Generates a prime number with a bit length of 31.
     *
     * @return random prime number
     */
    public static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    /**
     * Counts the number of lines in a file
     *
     * @param filePath the file path
     * @return         the number of lines
     */
    public static long getLinesNumber(final Path filePath) {

        try (Stream<String> stream = Files.lines(filePath)) {
            return stream.count();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to read number of lines from file '%s'.", filePath));
        }
    }

    /**
     * Generates a block of lines from a text
     *
     * @param filePath  the file path
     * @param startLine the start line of a block
     * @param blockSize the size of a block
     * @return          the stream of lines
     */
    public static Stream<String> getLines(final Path filePath, final long startLine,
                                          final int blockSize) {
        try {

            return Files.lines(filePath)
                    .skip(startLine)
                    .limit(blockSize);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to read a block of %d lines starting with line %d" +
                    " from file '%s'.", blockSize, startLine, filePath));
        }
    }

}

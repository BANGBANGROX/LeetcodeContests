import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Solution {
    public int[] countWordOccurrences(final String[] chunks, final String[] queries) {
        final StringBuilder concatenatedString = new StringBuilder();

        for (final String chunk : chunks) {
            concatenatedString.append(chunk);
        }

        final char[] charArray = concatenatedString.toString().toCharArray();

        for (int i = 0; i < charArray.length; ++i) {
            if (charArray[i] == '-') {
                if (!(i > 0 && i + 1 < charArray.length && Character.isAlphabetic(charArray[i - 1]) && Character.isAlphabetic(charArray[i + 1]))) {
                    charArray[i] = ' ';
                }
            }
        }

        final String finalString = new String(charArray);
        final Map<String, Integer> count = new HashMap<>();
        final int q = queries.length;
        final int[] answer = new int[q];

        for (final String s : finalString.split(" ")) {
            if (!s.isBlank()) {
                count.put(s, count.getOrDefault(s, 0) + 1);
            }
        }

        for (int i = 0; i < q; ++i) {
            answer[i] = count.getOrDefault(queries[i], 0);
        }

        return answer;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final String[] chunks = new String[n];

        for (int i = 0; i < n; ++i) {
            chunks[i] = scanner.nextLine();
        }

        final int q = scanner.nextInt();
        final String[] queries = new String[q];

        for (int i = 0; i < q; ++i) {
            queries[i] = scanner.next();
        }

        final int[] answer = new Solution().countWordOccurrences(chunks, queries);

        for (final int x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();

        scanner.close();
    }
}

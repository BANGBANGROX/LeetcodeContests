import java.util.Scanner;

class Solution {
    public int minFlips(final String s) {
        final int n = s.length();

        if (n < 3 || s.matches("^10*1$")) {
            return 0;
        }

        int[] count = new int[2];
        final String idealString = buildIdealString(n);
        int idealStringDifferences = 0;

        for (final char ch : s.toCharArray()) {
            ++count[ch - '0'];
        }

        if (count[1] == n || count[1] < 2) {
            return 0;
        }

        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) != idealString.charAt(i)) {
                ++idealStringDifferences;
            }
        }

        return Math.min(count[1] - 1, Math.min(count[0], idealStringDifferences));
    }

    private String buildIdealString(final int n) {
        return 1 +
                "0".repeat(Math.max(0, n - 2)) +
                1;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String s = scanner.next();

        System.out.println(new Solution().minFlips(s));

        scanner.close();
    }
}

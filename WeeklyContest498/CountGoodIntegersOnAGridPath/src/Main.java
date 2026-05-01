import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Solution {
    private boolean[] onPath;
    private long[][][][] dp;
    private int[] number;

    public long countGoodIntegersOnPath(final long l, final long r, final String directions) {
        final Map<Character, List<Integer>> charToDirectionMap = Map.of(
                'D', List.of(1, 0),
                'R', List.of(0, 1)
        );
        onPath = new boolean[16];
        int x = 0;
        int y = 0;
        final int gridCols = 4;

        onPath[0] = true;

        for (final char direction : directions.toCharArray()) {
            final List<Integer> numericDirection = charToDirectionMap.get(direction);

            x += numericDirection.get(0);
            y += numericDirection.get(1);

            final int idx = x * gridCols + y;
            onPath[idx] = true;
        }

        final long integersTillR = countGoodIntegersLessThanN(r);

        return l > 0 ? integersTillR - countGoodIntegersLessThanN(l - 1) : integersTillR;
    }

    private long countGoodIntegersLessThanN(final long n) {
        number = new int[16];
        final String stringNumber = String.valueOf(n);
        final int leadingZeroesNeeded = 16 - stringNumber.length();
        int ptr = 0;

        for (;ptr < leadingZeroesNeeded; ++ptr) {
            number[ptr] = 0;
        }

        for (final char ch : stringNumber.toCharArray()) {
            number[ptr] = (ch - '0');
            ++ptr;
        }

        dp = new long[16][11][2][2];

        for (final long[][][] x : dp) {
            for (final long[][] y : x) {
                for (final long[] z : y) {
                    Arrays.fill(z, -1);
                }
            }
        }

        return dfs(0, -1, true, true);
    }

    private long dfs(final int pos, final int last, final boolean isLimit, final boolean isLeading) {
        if (pos == 16) {
            return isLeading ? 0 : 1;
        }

        if (dp[pos][last + 1][getBooleanValueInt(isLimit)][getBooleanValueInt(isLeading)] != -1) {
            return dp[pos][last + 1][getBooleanValueInt(isLimit)][getBooleanValueInt(isLeading)];
        }

        long count = 0;
        final int globalMaxDigit = 9;
        final int maxDigitAvb = isLimit ? number[pos] : globalMaxDigit;

        for (int digit = 0; digit <= maxDigitAvb; ++digit) {
            final boolean nextIsLeading = isLeading && (digit == 0);
            final boolean nextIsLimit = isLimit && (digit == maxDigitAvb);

            if (onPath[pos]) {
                if (nextIsLeading) {
                    count += dfs(pos + 1, -1, nextIsLimit, true);
                } else if (digit >= last) {
                    count += dfs(pos + 1, digit, nextIsLimit, false);
                }
            } else {
                count += dfs(pos + 1, last, nextIsLimit, nextIsLeading);
            }
        }

        return dp[pos][last + 1][getBooleanValueInt(isLimit)][getBooleanValueInt(isLeading)] = count;
    }

    private int getBooleanValueInt(final boolean val) {
        return val ? 1 : 0;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println(new Solution().
                countGoodIntegersOnPath(scanner.nextLong(), scanner.nextLong(), scanner.next())
        );

        scanner.close();
    }
}

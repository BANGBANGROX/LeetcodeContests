import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class Solution {
    public int longestBalanced(final String s) {
        final Map<Integer, List<Integer>> map = new HashMap<>();
        final int n = s.length();
        final int[] onesPrefixSum = new int[n];
        final int[] zeroesPrefixSum = new int[n];
        int runningSum = 0;
        int answer = 0;

        onesPrefixSum[0] = (s.charAt(0) == '1' ? 1 : 0);
        zeroesPrefixSum[0] = (s.charAt(0) == '1' ? 0 : 1);

        for (int i = 1; i < n; ++i) {
            onesPrefixSum[i] = onesPrefixSum[i - 1] + (s.charAt(i) == '1' ? 1 : 0);
            zeroesPrefixSum[i] = zeroesPrefixSum[i - 1] + (s.charAt(i) == '1' ? 0 : 1);
        }

        final int totalOnes = onesPrefixSum[n - 1];
        final int totalZeroes = zeroesPrefixSum[n - 1];

        if (totalOnes == 0 || totalZeroes == 0) {
            return 0;
        }

        if (totalOnes == 1 || totalZeroes == 1) {
            return 2;
        }

        map.put(0, new ArrayList<>(List.of(-1, -1)));

        for (int i = 0; i < n; ++i) {
            final int val = (s.charAt(i) == '1' ? 1 : -1);

            runningSum += val;

            if (map.containsKey(runningSum)) {
                answer = Math.max(answer, i - map.get(runningSum).getFirst());
            }

            if (map.containsKey(runningSum + 2)) {
                final int idx1 = map.get(runningSum + 2).get(0);
                final int idx2 = map.get(runningSum + 2).get(1);
                final int idx1ToIOnesCount = (onesPrefixSum[i] - (idx1 > 0 ? onesPrefixSum[idx1] : 0));
                final int idx2ToIOnesCount = (onesPrefixSum[i] - (idx2 > 0 ? onesPrefixSum[idx2] : 0));

                if (idx1ToIOnesCount < totalOnes) {
                    answer = Math.max(answer, i - idx1);
                } else if (idx2ToIOnesCount < totalOnes) {
                    answer = Math.max(answer, i - idx2);
                }
            }

            if (map.containsKey(runningSum - 2)) {
                final int idx1 = map.get(runningSum - 2).get(0);
                final int idx2 = map.get(runningSum - 2).get(1);
                final int idx1ToIZeroesCount = (zeroesPrefixSum[i] - (idx1 > 0 ? zeroesPrefixSum[idx1] : 0));
                final int idx2ToIZeroesCount = (zeroesPrefixSum[i] - (idx2 > 0 ? zeroesPrefixSum[idx2] : 0));

                if (idx1ToIZeroesCount < totalZeroes) {
                    answer = Math.max(answer, i - idx1);
                } else if (idx2ToIZeroesCount < totalZeroes) {
                    answer = Math.max(answer, i - idx2);
                }
            }

            if (map.containsKey(runningSum)) {
                if (Objects.equals(map.get(runningSum).get(0), map.get(runningSum).get(1))) {
                    map.get(runningSum).set(1, i);
                }
            } else {
                map.computeIfAbsent(runningSum, k -> new ArrayList<>()).addAll(List.of(i, i));
            }
        }

        return answer;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String s = scanner.next();

        System.out.println(new Solution().longestBalanced(s));

        scanner.close();
    }
}

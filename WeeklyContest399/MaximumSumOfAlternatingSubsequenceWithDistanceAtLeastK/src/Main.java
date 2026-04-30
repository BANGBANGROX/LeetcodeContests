import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class RangeMaxSegmentTree {
    private final long[] tree;
    private final long defaultValue;
    private final int n;

    public RangeMaxSegmentTree(final int n, final long defaultValue) {
        tree = new long[4 * n];
        this.defaultValue = defaultValue;
        this.n = n;

        Arrays.fill(tree, defaultValue);
    }

    public void update(final int idx, final long val) {
        update(1, 0, n - 1, idx, val);
    }

    public long query(final int left, final int right) {
        if (left > right) {
            return defaultValue;
        }

        return query(1, left, right, 0, n - 1);
    }

    private void update(final int node, final int start, final int end, final int idx, final long val) {
        if (start == end) {
            tree[node] = Math.max(tree[node], val);
            return;
        }

        final int mid = (start + end) / 2;

        if (idx <= mid) {
            update(2 * node, start, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, end, idx, val);
        }

        tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
    }

    private long query(final int node, final int left, final int right, final int start, final int end) {
        if (end < left || start > right) {
            return defaultValue;
        }

        if (left <= start && end <= right) {
            return tree[node];
        }

        final int mid = (start + end) / 2;
        final long firstHalf = query(2 * node, left, right, start, mid);
        final long secondHalf = query(2 * node + 1, left, right, mid + 1, end);

        return Math.max(firstHalf, secondHalf);
    }
}

class Solution {
    public long maxAlternatingSum(final int[] nums, final int k) {
        final int n = nums.length;
        final Map<Integer, Integer> numRank = new HashMap<>();
        final long[] dpUp = new long[n]; // Max sum of subsequence ending at i when nums[i] > prev
        final long[] dpDown = new long[n]; // Max sum of subsequence ending at i when nums[i] < prev
        final int[] sortedNums = nums.clone();
        long answer = Long.MIN_VALUE;
        int runningRank = 0;

        Arrays.sort(sortedNums);

        for (final int num : sortedNums) {
            if (!numRank.containsKey(num)) {
                numRank.put(num, runningRank);
                ++runningRank;
            }
        }

        final RangeMaxSegmentTree upTree = new RangeMaxSegmentTree(runningRank, 0);
        final RangeMaxSegmentTree downTree = new RangeMaxSegmentTree(runningRank, 0);

        for (int i = 0; i < n; ++i) {
            final int updateIdx = i - k;
            final int currRank = numRank.get(nums[i]);

            if (updateIdx >= 0) {
                final int prevRank = numRank.get(nums[updateIdx]);

                upTree.update(prevRank, dpUp[updateIdx]);
                downTree.update(prevRank, dpDown[updateIdx]);
            }

            final long bestPrevLow = downTree.query(0, currRank - 1);
            final long bestPrevHigh = upTree.query(currRank + 1, runningRank - 1);

            dpUp[i] = bestPrevLow + nums[i];
            dpDown[i] = bestPrevHigh > 0 ? bestPrevHigh + nums[i] : 0;

            answer = Math.max(answer, Math.max(dpUp[i], dpDown[i]));
        }

        return answer;
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = scanner.nextInt();
        }
        final int k = scanner.nextInt();

        System.out.println(new Solution().maxAlternatingSum(nums, k));
    }
}
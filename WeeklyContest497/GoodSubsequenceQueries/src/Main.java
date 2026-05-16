import java.util.Scanner;

class SegmentTree {
    private final int[] tree;
    private final int n;

    public SegmentTree(final int n) {
        tree = new int[4 * n];
        this.n = n;
    }

    public void build(final int[] nums) {
        build(1, 0, n - 1, nums);
    }

    public void update(final int index, final int val) {
        update(1, 0, n - 1, index, val);
    }

    public boolean canRemoveOne(final int externalGcd) {
        return canRemoveOne(1, 0, n - 1, externalGcd);
    }

    public boolean checkRootGcd(final int expectedGcd) {
        return tree[1] == expectedGcd;
    }

    private boolean canRemoveOne(final int node, final int left, final int right, final int externalGcd) {
        if (left == right) {
            return externalGcd == 1;
        }

        final int externalToLeftGcd = Solution.calculateGCD(externalGcd, tree[2 * node + 1]);
        final int externalToRightGcd = Solution.calculateGCD(externalGcd, tree[2 * node]);

        if (externalToLeftGcd == 1 || externalToRightGcd == 1) {
            return true;
        }

        final int mid = (left + ((right - left) >> 1));

        return canRemoveOne(2 * node, left, mid, externalToLeftGcd) ||
                canRemoveOne(2 * node + 1, mid + 1, right, externalToRightGcd);
    }

    private void update(final int node, final int left, final int right, final int index, final int val) {
        if (left == right) {
            tree[node] = val;
            return;
        }

        final int mid = (left + ((right - left) >> 1));

        if (index <= mid) {
            update(2 * node, left, mid, index, val);
        } else {
            update(2 * node + 1, mid + 1, right, index, val);
        }

        tree[node] = Solution.calculateGCD(tree[2 * node], tree[2 * node + 1]);
    }

    private void build(final int node, final int left, final int right, final int[] nums) {
        if (left == right) {
            tree[node] = nums[left];
            return;
        }

        final int mid = (left + ((right - left) >> 1));

        build(2 * node, left, mid, nums);
        build(2 * node + 1, mid + 1, right, nums);

        tree[node] = Solution.calculateGCD(tree[2 * node], tree[2 * node + 1]);
    }
}

class Solution {
    public int countGoodSubseq(final int[] nums, final int p, final int[][] queries) {
        final int n = nums.length;
        final SegmentTree segmentTree = new SegmentTree(n);
        int totalNumbersDivisibleByP = 0;
        int answer = 0;

        for (int i = 0; i < n; ++i) {
            if (nums[i] % p == 0) {
                nums[i] /= p;
                ++totalNumbersDivisibleByP;
            } else {
                nums[i] = 0;
            }
        }

        segmentTree.build(nums);

        for (final int[] query : queries) {
            final int index = query[0];
            final int value = (query[1] % p == 0 ? query[1] / p : 0);

            if (nums[index] != 0) {
                --totalNumbersDivisibleByP;
            }

            nums[index] = value;

            if (nums[index] != 0) {
                ++totalNumbersDivisibleByP;
            }

            segmentTree.update(index, value);

            if (segmentTree.checkRootGcd(1) && (totalNumbersDivisibleByP < n || segmentTree.canRemoveOne(0))) {
                ++answer;
            }
        }

        return answer;
    }

    public static int calculateGCD(final int a, final int b) {
        if (a == 0) {
            return b;
        }

        return calculateGCD(b % a, a);
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int[] nums = new int[n];

        for (int i = 0; i < n; ++i) {
            nums[i] = scanner.nextInt();
        }

        final int p = scanner.nextInt();
        final int q = scanner.nextInt();
        final int[][] queries = new int[q][2];

        for (int i = 0; i < q; ++i) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }

        System.out.println(new Solution().countGoodSubseq(nums, p, queries));

        scanner.close();
    }
}

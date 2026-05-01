import java.util.Scanner;

class Solution {
    public int firstStableIndex(final int[] nums, final int k) {
        final int n = nums.length;
        final int[] suffixMin = new int[n];

        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; --i) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }

        int leftMax = Integer.MIN_VALUE;

        for (int i = 0; i < n; ++i) {
            leftMax = Math.max(leftMax, nums[i]);
            final int rightMin = suffixMin[i];

            if (leftMax - rightMin <= k) {
                return i;
            }
        }

        return -1;
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
        final int k = scanner.nextInt();

        System.out.println(new Solution().firstStableIndex(nums, k));

        scanner.close();
    }
}

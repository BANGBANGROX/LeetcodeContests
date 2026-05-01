import java.util.Scanner;

class Solution {
    public int firstStableIndex(final int[] nums, final int k) {
        final int n = nums.length;
        int leftMax = Integer.MIN_VALUE;

        for (int i = 0; i < n; ++i) {
            leftMax = Math.max(leftMax, nums[i]);
            int rightMin = nums[i];

            for (int j = i + 1; j < n; ++j) {
                rightMin = Math.min(rightMin, nums[j]);
            }

            final int instabilityIndex = leftMax - rightMin;

            if (instabilityIndex <= k) {
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

import java.util.Scanner;

class Solution {
    public int[] minCost(final int[] nums, final int[][] queries) {
        final int n = nums.length;
        final int q = queries.length;
        final int[] forwardDistance = new int[n];
        final int[] backwardDistance = new int[n];
        final int[] answer = new int[q];

        for (int i = 0; i < n; ++i) {
            final int leftDistance = (i > 0 ? nums[i] - nums[i - 1] : Integer.MAX_VALUE);
            final int rightDistance = (i < n - 1 ? nums[i + 1] - nums[i] : Integer.MAX_VALUE);

            if (leftDistance <= rightDistance) {
                backwardDistance[i] = 1; // (i, i - 1)
                forwardDistance[i] = rightDistance; // (i - 1, i)
            } else {
                backwardDistance[i] = leftDistance;
                forwardDistance[i] = 1;
            }
        }

        for (int i = 1; i < n - 1; ++i) {
            forwardDistance[i] += forwardDistance[i - 1];
        }

        for (int i = n - 2; i >= 1; --i) {
            backwardDistance[i] += backwardDistance[i + 1];
        }

        for (int i = 0; i < q; ++i) {
            final int left = queries[i][0];
            final int right = queries[i][1];

            if (left < right) {
                answer[i] = forwardDistance[right - 1] - (left > 0 ? forwardDistance[left - 1] : 0);
            } else if (left > right) {
                answer[i] = backwardDistance[right + 1] - (left < n - 1 ? backwardDistance[left + 1] : 0);
            } else {
                answer[i] = 0;
            }

            answer[i] = Math.min(answer[i], Math.abs(nums[left] - nums[right]));
        }

        return answer;
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
        final int q = scanner.nextInt();
        final int[][] queries = new int[q][2];
        for (int i = 0; i < q; ++i) {
            queries[i][0] = scanner.nextInt();
            queries[i][1] = scanner.nextInt();
        }

        final int[] answer = new Solution().minCost(nums, queries);
        for (final int x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();

        scanner.close();
    }
}

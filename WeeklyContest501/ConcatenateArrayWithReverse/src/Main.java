import java.util.Scanner;

class Solution {
    public int[] concatWithReverse(final int[] nums) {
        final int n = nums.length;
        final int[] answer = new int[2 * n];
        int ptr = 0;

        for (int i = 0; i < 2 * n; ++i) {
            if (i == n) {
                ptr = n - 1;
            }

            answer[i] = nums[ptr];

            if (i < n) {
                ++ptr;
            } else {
                --ptr;
            }
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

        final int[] answer = new Solution().concatWithReverse(nums);

        for (final int x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();

        scanner.close();
    }
}

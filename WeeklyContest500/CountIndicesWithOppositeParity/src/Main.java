import java.util.Scanner;

class Solution {
    public int[] countOppositeParity(final int[] nums) {
        final int n = nums.length;
        final int[] answer = new int[n];

        for (int i = 0; i < n; ++i) {
            final int currentParity = nums[i] % 2;
            for (int j = i + 1; j < n; ++j) {
                if (currentParity != (nums[j] % 2)) {
                    ++answer[i];
                }
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

        final int[] answer = new Solution().countOppositeParity(nums);
        for (final int x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();

        scanner.close();
    }
}

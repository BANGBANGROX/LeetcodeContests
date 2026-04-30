import java.util.Scanner;

class Solution {
    public long minOperations(final int[] nums) {
        final int n = nums.length;
        long answer = 0;

        for (int i = 1; i < n; ++i) {
            if (nums[i - 1] > nums[i]) {
                answer += (nums[i - 1] - nums[i]);
            }
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

        System.out.println(new Solution().minOperations(nums));
    }
}
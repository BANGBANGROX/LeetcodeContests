import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Solution {
    public List<Integer> findValidElements(final int[] nums) {
        final List<Integer> answer = new ArrayList<>();
        final int n = nums.length;
        final int[] suffixMaximum = new int[n];
        int leftRunningMax = nums[0];

        suffixMaximum[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; --i) {
            suffixMaximum[i] = Math.max(suffixMaximum[i + 1], nums[i]);
        }

        answer.add(nums[0]);

        for (int i = 1; i < n - 1; ++i) {
            if (nums[i] > leftRunningMax || nums[i] > suffixMaximum[i + 1]) {
                answer.add(nums[i]);
            }

            leftRunningMax = Math.max(leftRunningMax, nums[i]);
        }

        if (n > 1) {
            answer.add(nums[n - 1]);
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

        System.out.print(new Solution().findValidElements(nums));

        scanner.close();
    }
}
import java.util.Arrays;
import java.util.Scanner;

class Solution {
    public long minArraySum(final int[] nums) {
        long answer = 0;
        final boolean[] visited = new boolean[Arrays.stream(nums).max().getAsInt() + 1];

        for (final int num : nums) {
            visited[num] = true;
        }

        for (final int num : nums) {
            int factor = num;

            for (int i = 1; i * i <= num; ++i) {
                if (num % i == 0) {
                    if (visited[i]) {
                        factor = Math.min(factor, i);
                        break;
                    } else if (visited[num / i]) {
                        factor = Math.min(factor, num / i);
                    }
                }
            }

            answer += factor;
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

        System.out.println(new Solution().minArraySum(nums));

        scanner.close();
    }
}

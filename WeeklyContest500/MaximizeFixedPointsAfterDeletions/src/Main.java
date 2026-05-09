import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Solution {
    public int maxFixedPoints(final int[] nums) {
        final int n = nums.length;
        final List<List<Integer>> distanceAndValueList = new ArrayList<>();
        final List<Integer> dp = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            if (i >= nums[i]) {
                distanceAndValueList.add(List.of(i - nums[i], nums[i]));
            }
        }

        distanceAndValueList.sort((a, b) ->
                !Objects.equals(a.getFirst(), b.getFirst()) ? a.getFirst() - b.getFirst() : a.get(1)
        );

        for (final List<Integer> list : distanceAndValueList) {
            if (dp.isEmpty() || list.get(1) > dp.getLast()) {
                dp.add(list.get(1));
            } else {
                int idx = lowerBound(dp, list.get(1));
                dp.set(idx, list.get(1));
            }
        }

        return dp.size();
    }

    private int lowerBound(final List<Integer> list, final int key) {
        int left = 0;
        int right = list.size() - 1;
        int answer = -1;

        while (left <= right) {
            final int mid = (left + right) / 2;

            if (list.get(mid) >= key) {
                right = mid - 1;
                answer = mid;
            } else {
                left = mid + 1;
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

        System.out.println(new Solution().maxFixedPoints(nums));

        scanner.close();
    }
}

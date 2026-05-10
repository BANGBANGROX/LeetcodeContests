import java.util.Map;
import java.util.Scanner;

class Solution {
    public int[] scoreValidator(final String[] events) {
        final Map<String, Integer> eventToScoreChangeMap = Map.of(
                "0", 0,
                "1", 1,
                "2", 2,
                "3", 3,
                "4", 4,
                "5", 5,
                "6", 6,
                "WD", 1,
                "NB", 1
        );
        int score = 0;
        int counter = 0;

        for (final String event : events) {
            if (eventToScoreChangeMap.containsKey(event)) {
                score += eventToScoreChangeMap.get(event);
            } else {
                ++counter;

                if (counter == 10) {
                    break;
                }
            }
        }

        return new int[]{score, counter};
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final String[] events = new String[n];

        for (int i = 0; i < n; ++i) {
            events[i] = scanner.next();
        }

        final int[] answer = new Solution().scoreValidator(events);
        System.out.println("Score = " + answer[0] + "\n Counter = " + answer[1]);

        scanner.close();
    }
}

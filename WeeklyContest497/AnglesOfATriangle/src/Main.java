import java.util.Arrays;
import java.util.Scanner;

class Solution {
    public double[] internalAngles(int[] sides) {
        Arrays.sort(sides);

        if (sides[2] >= sides[0] + sides[1]) {
            return new double[]{};
        }

        final int a = sides[0];
        final int b = sides[1];
        final int c = sides[2];
        final double angleC = Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2.0 * a * b)));
        final double angleB = Math.toDegrees(Math.acos((a * a + c * c - b * b) / (2.0 * a * c)));
        final double angleA = Math.toDegrees(Math.acos((b * b + c * c - a * a) / (2.0 * b * c));
        final double[] answer = {angleA, angleB, angleC};

        Arrays.sort(answer);

        return answer;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int[] sides = new int[3];

        for (int i = 0; i < 3; ++i) {
            sides[0] = scanner.nextInt();
            sides[1] = scanner.nextInt();
            sides[2] = scanner.nextInt();
        }

        final double[] answer = new Solution().internalAngles(sides);

        for (final double x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();

        scanner.close();
    }
}

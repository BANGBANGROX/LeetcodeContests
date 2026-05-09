import java.util.Scanner;

class Solution {
    public int sumOfPrimesInRange(final int num) {
        final int reverseNum = getNumberReverse(num);
        final int left = Math.min(num, reverseNum);
        final int right = Math.max(num, reverseNum);
        int answer = 0;

        for (int i = left; i <= right; ++i) {
            if (isPrime(i)) {
                answer += i;
            }
        }

        return answer;
    }

    private int getNumberReverse(final int num) {
        return Integer.parseInt(new StringBuilder("" + num).reverse().toString());
    }

    private boolean isPrime(final int num) {
        if (num == 1) {
            return false;
        }

        for (int i = 2; i * i <= num; ++i) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        System.out.println(new Solution().sumOfPrimesInRange(scanner.nextInt()));

        scanner.close();
    }
}

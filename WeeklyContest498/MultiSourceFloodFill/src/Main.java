import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Solution {
    public int[][] colorGrid(final int rows, final int cols, final int[][] sources) {
        final int[][] answer = new int[rows][cols];
        final ArrayDeque<List<Integer>> queue = new ArrayDeque<>();
        final int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        Arrays.sort(sources, (a, b) -> b[2] - a[2]);

        for (final int[] source : sources) {
            final int i = source[0];
            final int j = source[1];
            final int c = source[2];

            answer[i][j] = c;
            queue.addLast(List.of(i, j));
        }

        while (!queue.isEmpty()) {
            final int size = queue.size();

            for (int i = 0; i < size; ++i) {
                final List<Integer> cell = queue.pollFirst();
                final int x = cell.get(0);
                final int y = cell.get(1);

                for (final int[] direction : directions) {
                    final int newX = x + direction[0];
                    final int newY = y + direction[1];

                    if (newX >= 0 && newY >= 0 && newX < rows && newY < cols && answer[newX][newY] == 0) {
                        answer[newX][newY] = answer[x][y];
                        queue.addLast(List.of(newX, newY));
                    }
                }
            }
        }

        return answer;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int rows = scanner.nextInt();
        final int cols = scanner.nextInt();
        final int n = scanner.nextInt();
        final int[][] sources = new int[n][3];
        for (int i = 0; i < n; ++i) {
            sources[i][0] = scanner.nextInt();
            sources[i][1] = scanner.nextInt();
            sources[i][2] = scanner.nextInt();
        }

        final int[][] answer = new Solution().colorGrid(rows, cols, sources);
        for (final int[] currentRow : answer) {
            for (final int color : currentRow) {
                System.out.print(color + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}

import java.util.Scanner;

class Solution {
    public int minGenerations(final int[][] points, final int[] target) {
        // 1. Primitive Queue to avoid boxing
        final int[] queue = new int[1000];
        final boolean[] visited = new boolean[700];
        final int targetPoint = target[0] * 100 + target[1] * 10 + target[2];
        int tail = 0;

        for (final int[] point : points) {
            int packed = point[0] * 100 + point[1] * 10 + point[2];

            if (packed == targetPoint) return 0;

            if (!visited[packed]) {
                visited[packed] = true;
                queue[tail++] = packed;
            }
        }

        int generation = 0;
        int head = 0;
        int currentGenEnd = tail;

        // Standard BFS structure
        while (head < currentGenEnd && tail < 1000) {
            generation++;
            for (int i = 0; i < currentGenEnd; i++) {
                for (int j = i + 1; j < currentGenEnd; j++) {
                    // Inline the averaging math here...
                    final int next = computeNextPoint(queue[i], queue[j]);
                    if (!visited[next]) {
                        if (next == targetPoint) return generation;
                        visited[next] = true;
                        if (tail < 1000) queue[tail++] = next;
                    }
                }
            }
            head = currentGenEnd;
            currentGenEnd = tail;
        }
        return -1;
    }

    private int computeNextPoint(final int p1, final int p2) {
        // Unpack Point 1
        final int x1 = p1 / 100;
        final int y1 = (p1 / 10) % 10;
        final int z1 = p1 % 10;

        // Unpack Point 2
        final int x2 = p2 / 100;
        final int y2 = (p2 / 10) % 10;
        final int z2 = p2 % 10;

        // Compute Averages and Repack
        final int nextX = (x1 + x2) / 2;
        final int nextY = (y1 + y2) / 2;
        final int nextZ = (z1 + z2) / 2;

        return nextX * 100 + nextY * 10 + nextZ;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int[][] points = new int[n][3];

        for (int i = 0; i < n; ++i) {
            points[i][0] = scanner.nextInt();
            points[i][1] = scanner.nextInt();
            points[i][2] = scanner.nextInt();
        }

        final int[] target = new int[3];
        target[0] = scanner.nextInt();
        target[1] = scanner.nextInt();
        target[2] = scanner.nextInt();

        System.out.println(new Solution().minGenerations(points, target));

        scanner.close();
    }
}

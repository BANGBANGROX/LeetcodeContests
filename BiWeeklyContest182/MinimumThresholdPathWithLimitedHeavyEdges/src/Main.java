import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

class Solution {
    private List<List<List<Integer>>> graph;
    private int n;
    private int source;
    private int target;
    private int k;

    public int minimumThreshold(int n, int[][] edges, int source, int target, int k) {
        graph = new ArrayList<>();
        int maxWeight = 0;
        this.n = n;
        this.source = source;
        this.target = target;
        this.k = k;

        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            final int u = edge[0];
            final int v = edge[1];
            final int wt = edge[2];

            graph.get(u).add(List.of(v, wt));
            graph.get(v).add(List.of(u, wt));

            maxWeight = Math.max(maxWeight, wt);
        }

        int left = 0;
        int right = maxWeight;
        int answer = -1;

        while (left <= right) {
            final int mid = (left + ((right - left) >> 1));

            if (check(mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private boolean check(final int threshold) {
        final Deque<Integer> deque = new ArrayDeque<>();
        final int[] distance = new int[n];

        Arrays.fill(distance, Integer.MAX_VALUE);

        deque.add(source);
        distance[source] = 0;

        while (!deque.isEmpty()) {
            final int node = deque.pollFirst();

            for (final List<Integer> childNode : graph.get(node)) {
                final int child = childNode.get(0);
                final int wt = childNode.get(1) > threshold ? 1 : 0;

                if (distance[child] > distance[node] + wt) {
                    distance[child] = distance[node] + wt;
                    if (wt == 0) {
                        deque.addFirst(child);
                    } else {
                        deque.addLast(child);
                    }
                }
            }
        }

        return distance[target] <= k;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        final int[][] edges = new int[m][3];

        for (int i = 0; i < m; ++i) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
            edges[i][2] = scanner.nextInt();
        }

        final int source = scanner.nextInt();
        final int target = scanner.nextInt();
        final int k = scanner.nextInt();

        System.out.println(new Solution().minimumThreshold(n, edges, source, target, k));

        scanner.close();
    }
}

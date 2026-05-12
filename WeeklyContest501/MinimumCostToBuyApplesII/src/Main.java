import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

class Solution {
    private static final long INF = (long) 1e15;

    private static class GraphNode {
        int node;
        long weight;

        GraphNode(final int node, final long weight) {
            this.node = node;
            this.weight = weight;
        }

        @Override
        public boolean equals(final Object object) {
            if (!(object instanceof GraphNode graphNode)) {
                return false;
            }

            return graphNode.node == node && graphNode.weight == weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(node, weight);
        }
    }

    public int[] minCost(final int n, final int[] prices, final int[][] roads) {
        final long[][] costWithoutApples = computeCost(n, roads, false);
        final long[][] costWithApples = computeCost(n, roads, true);
        final int[] answer = new int[n];

        for (int i = 0; i < n; ++i) {
            answer[i] = prices[i];

            for (int j = 0; j < n; ++j) {
                if (i == j) {
                    continue;
                }

                final long alternatePrice = costWithoutApples[i][j] + costWithApples[j][i] + prices[j];

                if (answer[i] > alternatePrice) {
                    answer[i] = (int) alternatePrice;
                }
            }
        }

        return answer;
    }

    private long[][] computeCost(final int n, final int[][] roads, final boolean includeTax) {
        final List<List<GraphNode>> graph = new ArrayList<>();
        final long[][] distance = new long[n][n];

        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }

        for (final int[] road : roads) {
            final int u = road[0];
            final int v = road[1];
            final int cost = road[2];
            final int tax = includeTax ? road[3] : 1;

            graph.get(u).add(new GraphNode(v, (long) cost * tax));
            graph.get(v).add(new GraphNode(u, (long) cost * tax));
        }

        for (int i = 0; i < n; ++i) {
            distance[i] = computeShortestPaths(n, i, graph);
        }

        return distance;
    }

    private long[] computeShortestPaths(final int n, final int source, final List<List<GraphNode>> graph) {
        final long[] distance = new long[n];
        final PriorityQueue<GraphNode> priorityQueue = new PriorityQueue<>((a, b) -> {
            if (a.weight != b.weight) return Long.compare(a.weight, b.weight);
            return Integer.compare(a.node, b.node);
        });

        Arrays.fill(distance, INF);
        distance[source] = 0;
        priorityQueue.offer(new GraphNode(source, distance[source]));

        while (!priorityQueue.isEmpty()) {
            final GraphNode node = priorityQueue.poll();
            final int currentNode = node.node;

            if (distance[currentNode] < node.weight) {
                continue;
            }

            for (final GraphNode childNode : graph.get(currentNode)) {
                final int child = childNode.node;
                final long weight = childNode.weight;

                if (distance[child] > distance[currentNode] + weight) {
                    distance[child] = distance[currentNode] + weight;
                    priorityQueue.offer(new GraphNode(child, distance[child]));
                }
            }
        }

        return distance;
    }
}

public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int[] prices = new int[n];

        for (int i = 0; i < n; ++i) {
            prices[i] = scanner.nextInt();
        }

        final int m = scanner.nextInt();
        final int[][] roads = new int[m][4];

        for (int i = 0; i < m; ++i) {
            roads[i][0] = scanner.nextInt();
            roads[i][1] = scanner.nextInt();
            roads[i][2] = scanner.nextInt();
            roads[i][3] = scanner.nextInt();
        }

        final int[] answer = new Solution().minCost(n, prices, roads);

        for (final int x : answer) {
            System.out.print(x + " ");
        }
        System.out.println();

        scanner.close();
    }
}

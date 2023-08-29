package class060;

import java.util.Arrays;

public class Code02_BellmanFord {

	public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
		int[] cost = new int[n];
		Arrays.fill(cost, Integer.MAX_VALUE);
		cost[src] = 0;
		for (int i = 0; i <= k; i++) {
			int[] next = Arrays.copyOf(cost, n);
			for (int[] f : flights) {
				if (cost[f[0]] != Integer.MAX_VALUE) {
					next[f[1]] = Math.min(next[f[1]], cost[f[0]] + f[2]);
				}
			}
			cost = next;
		}
		return cost[dst] == Integer.MAX_VALUE ? -1 : cost[dst];
	}

}

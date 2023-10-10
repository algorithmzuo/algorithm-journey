package class064;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

// Dijkstra算法模版（Leetcode）
// 网络延迟时间
// 有 n 个网络节点，标记为 1 到 n
// 给你一个列表 times，表示信号经过 有向 边的传递时间
// times[i] = (ui, vi, wi)，其中 ui 是源节点
// vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间
// 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号
// 如果不能使所有节点收到信号，返回 -1
// 测试链接 : https://leetcode.cn/problems/network-delay-time
public class Code01_DijkstraLeetcode {

	// 动态建图+普通堆的实现
	public static int networkDelayTime1(int[][] times, int n, int k) {
		ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] delay : times) {
			graph.get(delay[0]).add(new int[] { delay[1], delay[2] });
		}
		int[] distance = new int[n + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		distance[k] = 0;
		boolean[] visited = new boolean[n + 1];
		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.add(new int[] { k, 0 });
		while (!heap.isEmpty()) {
			int[] record = heap.poll();
			int cur = record[0];
			int cost = record[1];
			if (visited[cur]) {
				continue;
			}
			visited[cur] = true;
			for (int[] edge : graph.get(cur)) {
				int v = edge[0];
				int w = edge[1];
				if (!visited[v] && cost + w < distance[v]) {
					distance[v] = cost + w;
					heap.add(new int[] { v, cost + w });
				}
			}
		}
		int ans = Integer.MIN_VALUE;
		for (int i = 1; i <= n; i++) {
			if (distance[i] == Integer.MAX_VALUE) {
				return -1;
			}
			ans = Math.max(ans, distance[i]);
		}
		return ans;
	}

	// 链式前向星+反向索引堆的实现
	public static int networkDelayTime2(int[][] times, int n, int k) {
		build(n);
		for (int[] edge : times) {
			addEdge(edge[0], edge[1], edge[2]);
		}
		addOrUpdateOrIgnore(k, 0);
		while (!isEmpty()) {
			int v = pop();
			for (int ei = head[v]; ei > 0; ei = next[ei]) {
				addOrUpdateOrIgnore(to[ei], distance[v] + weight[ei]);
			}
		}
		int ans = Integer.MIN_VALUE;
		for (int i = 1; i <= n; i++) {
			if (distance[i] == Integer.MAX_VALUE) {
				return -1;
			}
			ans = Math.max(ans, distance[i]);
		}
		return ans;
	}

	public static int MAXN = 101;

	public static int MAXM = 6001;

	// 链式前向星
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// 反向索引堆
	public static int[] heap = new int[MAXN];

	// where[v] = -1，表示v这个节点，从来没有进入过堆
	// where[v] = -2，表示v这个节点，已经弹出过了
	// where[v] = i(>=0)，表示v这个节点，在堆上的i位置
	public static int[] where = new int[MAXN];

	public static int heapSize;

	public static int[] distance = new int[MAXN];

	public static void build(int n) {
		cnt = 1;
		heapSize = 0;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(where, 1, n + 1, -1);
		Arrays.fill(distance, 1, n + 1, Integer.MAX_VALUE);
	}

	// 链式前向星建图
	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void addOrUpdateOrIgnore(int v, int w) {
		if (where[v] == -1) {
			heap[heapSize] = v;
			where[v] = heapSize++;
			distance[v] = w;
			heapInsert(where[v]);
		} else if (where[v] >= 0) {
			distance[v] = Math.min(distance[v], w);
			heapInsert(where[v]);
		}
	}

	public static void heapInsert(int i) {
		while (distance[heap[i]] < distance[heap[(i - 1) / 2]]) {
			swap(i, (i - 1) / 2);
			i = (i - 1) / 2;
		}
	}

	public static int pop() {
		int ans = heap[0];
		swap(0, --heapSize);
		heapify(0);
		where[ans] = -2;
		return ans;
	}

	public static void heapify(int i) {
		int l = 1;
		while (l < heapSize) {
			int best = l + 1 < heapSize && distance[heap[l + 1]] < distance[heap[l]] ? l + 1 : l;
			best = distance[heap[best]] < distance[heap[i]] ? best : i;
			if (best == i) {
				break;
			}
			swap(best, i);
			i = best;
			l = i * 2 + 1;
		}
	}

	public static boolean isEmpty() {
		return heapSize == 0;
	}

	public static void swap(int i, int j) {
		int tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
		where[heap[i]] = i;
		where[heap[j]] = j;
	}

}

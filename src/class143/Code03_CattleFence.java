package class143;

// 牛场围栏
// 测试链接 : https://www.luogu.com.cn/problem/P2662

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Code03_CattleFence {

	public static int MAXN = 101;

	public static int MAXV = 3001;

	public static int MAXM = 30001;

	public static int inf = Integer.MAX_VALUE;

	public static int n, m, x;

	public static int[] arr = new int[MAXN];

	public static boolean[] set = new boolean[MAXV];

	// 链式前向星需要
	public static int[] head = new int[MAXV];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static int[] weight = new int[MAXM];

	public static int cnt;

	// dijkstra算法需要
	public static PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);

	public static int[] distance = new int[MAXV];

	public static boolean[] visited = new boolean[MAXV];

	public static void prepare() {
		cnt = 1;
		heap.clear();
		Arrays.fill(set, false);
		Arrays.fill(head, 0, x, 0);
		Arrays.fill(distance, 0, x, inf);
		Arrays.fill(visited, 0, x, false);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void dijkstra() {
		heap.add(new int[] { 0, 0 });
		distance[0] = 0;
		int[] curData;
		int u, w;
		while (!heap.isEmpty()) {
			curData = heap.poll();
			u = (int) curData[0];
			w = curData[1];
			if (visited[u]) {
				continue;
			}
			visited[u] = true;
			for (int ei = head[u], v; ei > 0; ei = next[ei]) {
				v = to[ei];
				if (!visited[v] && distance[v] > w + weight[ei]) {
					distance[v] = w + weight[ei];
					heap.add(new int[] { v, distance[v] });
				}
			}
		}
	}

	public static int compute() {
		int ans = 0;
		if (x == 1) {
			ans = -1;
		} else {
			for (int i = 1; i <= n; i++) {
				for (int j = Math.max(1, arr[i] - m); j <= arr[i]; j++) {
					if (!set[j]) {
						set[j] = true;
						for (int k = 0; k < x; k++) {
							addEdge(k, (k + j) % x, j);
						}
					}
				}
			}
			dijkstra();
			for (int i = 1; i < x; i++) {
				if (distance[i] == inf) {
					ans = -1;
					break;
				}
				ans = Math.max(ans, distance[i] - x);
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		m = (int) in.nval;
		x = inf;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			arr[i] = (int) in.nval;
			x = Math.min(x, Math.max(1, arr[i] - m));
		}
		prepare();
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}

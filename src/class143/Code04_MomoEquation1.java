package class143;

// 墨墨的等式(dijkstra算法)
// 一共有n种正数，每种数可以选择任意个，个数不能是负数
// 那么一定有某些数值可以由这些数字累加得到
// 请问在[l...r]范围上，有多少个数能被累加得到
// 0 <= n <= 12
// 0 <= 数值范围 <= 5 * 10^5
// 1 <= l <= r <= 10^12
// 测试链接 : https://www.luogu.com.cn/problem/P2371
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Code04_MomoEquation1 {

	public static int MAXN = 500001;

	public static int MAXM = 5000001;

	public static int n, x;

	public static long l, r;

	// 链式前向星需要
	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXM];

	public static int[] to = new int[MAXM];

	public static long[] weight = new long[MAXM];

	public static int cnt;

	// dijkstra算法需要
	// 0 : 当前节点
	// 1 : 源点到当前点距离
	public static PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> a[1] <= b[1] ? -1 : 1);

	public static long[] distance = new long[MAXN];

	public static boolean[] visited = new boolean[MAXN];

	public static void prepare() {
		cnt = 1;
		heap.clear();
		Arrays.fill(head, 0, x, 0);
		Arrays.fill(distance, 0, x, Long.MAX_VALUE);
		Arrays.fill(visited, 0, x, false);
	}

	public static void addEdge(int u, int v, long w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void dijkstra() {
		heap.add(new long[] { 0, 0 });
		distance[0] = 0;
		long[] cur;
		int u;
		long w;
		while (!heap.isEmpty()) {
			cur = heap.poll();
			u = (int) cur[0];
			w = cur[1];
			if (visited[u]) {
				continue;
			}
			visited[u] = true;
			for (int ei = head[u], v; ei > 0; ei = next[ei]) {
				v = to[ei];
				if (!visited[v] && distance[v] > w + weight[ei]) {
					distance[v] = w + weight[ei];
					heap.add(new long[] { v, distance[v] });
				}
			}
		}
	}

	public static long compute() {
		dijkstra();
		long ans = 0;
		for (int i = 0; i < x; i++) {
			if (r >= distance[i]) {
				ans += (r - distance[i]) / x + 1;
			}
			if (l >= distance[i]) {
				ans -= (l - distance[i]) / x + 1;
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
		l = (long) in.nval - 1;
		in.nextToken();
		r = (long) in.nval;
		in.nextToken();
		x = (int) in.nval;
		prepare();
		for (int i = 2, vi; i <= n; i++) {
			in.nextToken();
			vi = (int) in.nval;
			for (int j = 0; j < x; j++) {
				addEdge(j, (j + vi) % x, vi);
			}
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

}

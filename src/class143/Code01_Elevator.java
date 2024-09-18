package class143;

// 跳楼机
// 一座大楼一共有h层，楼层编号1~h，有如下四种移动方式
// 1, 向上移动x层
// 2, 向上移动y层
// 3, 向上移动z层
// 4, 回到1层
// 假设你正在第1层，请问大楼里有多少楼层你可以到达
// 1 <= h <= 2^63 - 1
// 1 <= x、y、z <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P3403
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Code01_Elevator {

	public static int MAXN = 100001;

	public static int MAXM = 200001;

	public static long h;

	public static int x, y, z;

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

	// 来自讲解064，dijkstra算法
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
			if (distance[i] <= h) {
				ans += (h - distance[i]) / x + 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		Kattio io = new Kattio();
		h = io.nextLong() - 1;
		x = io.nextInt();
		y = io.nextInt();
		z = io.nextInt();
		prepare();
		for (int i = 0; i < x; i++) {
			addEdge(i, (i + y) % x, y);
			addEdge(i, (i + z) % x, z);
		}
		io.println(compute());
		io.flush();
		io.close();
	}

	// Kattio类IO效率很好，但还是不如StreamTokenizer
	// 只有StreamTokenizer无法正确处理时，才考虑使用这个类
	// 参考链接 : https://oi-wiki.org/lang/java-pro/
	public static class Kattio extends PrintWriter {
		private BufferedReader r;
		private StringTokenizer st;

		public Kattio() {
			this(System.in, System.out);
		}

		public Kattio(InputStream i, OutputStream o) {
			super(o);
			r = new BufferedReader(new InputStreamReader(i));
		}

		public Kattio(String intput, String output) throws IOException {
			super(output);
			r = new BufferedReader(new FileReader(intput));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreTokens())
					st = new StringTokenizer(r.readLine());
				return st.nextToken();
			} catch (Exception e) {
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}
	}

}

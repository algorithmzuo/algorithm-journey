package class121;

// 测试链接 : https://www.luogu.com.cn/problem/P3629

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code05_Patrol {

	public static int MAXN = 100001;

	public static int n;

	public static int k;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		diameter1 = 0;
		diameter2 = 0;
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static int start, end, diameter1;

	public static int[] dist = new int[MAXN];

	public static int[] path = new int[MAXN];

	public static void road() {
		dfs1(1, 0, 0);
		start = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[start]) {
				start = i;
			}
		}
		dfs1(start, 0, 0);
		end = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[end]) {
				end = i;
			}
		}
		diameter1 = dist[end];
	}

	public static void dfs1(int u, int f, int w) {
		path[u] = f;
		dist[u] = dist[f] + w;
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs1(to[e], u, weight[e]);
			}
		}
	}

	public static boolean[] visited = new boolean[MAXN];

	public static int diameter2;

	public static void dp(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (visited[u] && visited[v]) {
					weight[e] = -1;
				}
				dp(v, u);
			}
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				diameter2 = Math.max(diameter2, dist[u] + dist[v] + weight[e]);
				dist[u] = Math.max(dist[u], dist[v] + weight[e]);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		in.nextToken();
		k = (int) in.nval;
		build();
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v, 1);
			addEdge(v, u, 1);
		}
		out.println(compute());
		out.flush();
		out.close();
		br.close();
	}

	public static int compute() {
		road();
		if (k == 1) {
			return 2 * (n - 1) - diameter1 + 1;
		} else {
			for (int i = end; i != 0; i = path[i]) {
				visited[i] = true;
			}
			Arrays.fill(dist, 1, n + 1, 0);
			dp(1, 0);
			return n * 2 - diameter1 - diameter2;
		}
	}

}
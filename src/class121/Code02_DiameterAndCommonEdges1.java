package class121;

// 测试链接 : https://www.luogu.com.cn/problem/P3304

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_DiameterAndCommonEdges1 {

	public static int MAXN = 200001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static boolean[] visited = new boolean[MAXN];

	public static long[] dist = new long[MAXN];

	public static long maxDist;

	public static int commonEdges;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(visited, 1, n + 1, false);
		maxDist = 0;
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static int start, end;

	public static long diameter;

	public static int[] path = new int[MAXN];

	public static void sedp() {
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
		diameter = dist[end];
	}

	public static void dfs1(int u, int f, long c) {
		path[u] = f;
		dist[u] = c;
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				dfs1(to[e], u, c + weight[e]);
			}
		}
	}

	public static void dfs2(int u, int f, long c) {
		dist[u] = c;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (!visited[v] && v != f) {
				dfs2(v, u, c + weight[e]);
			}
		}
		maxDist = Math.max(maxDist, dist[u]);
	}

	public static void compute() {
		sedp();
		for (int i = end; i != 0; i = path[i]) {
			visited[i] = true;
		}
		int l = start;
		int r = end;
		boolean flag = false;
		for (int i = path[end]; i != start; i = path[i]) {
			long ldist = dist[i], rdist = dist[end] - dist[i];
			dist[i] = maxDist = 0;
			dfs2(i, 0, 0);
			if (maxDist == rdist) {
				r = i;
			}
			if (maxDist == ldist && !flag) {
				flag = true;
				l = i;
			}
		}
		commonEdges = 1;
		for (int i = path[r]; i != l; i = path[i]) {
			commonEdges++;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		for (int i = 1, u, v, w; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			in.nextToken();
			w = (int) in.nval;
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		compute();
		out.println(diameter);
		out.println(commonEdges);
		out.flush();
		out.close();
		br.close();
	}

}

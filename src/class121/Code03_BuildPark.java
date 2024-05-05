package class121;

// 测试链接 : https://www.luogu.com.cn/problem/P2195

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_BuildPark {

	public static int MAXN = 300001;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] father = new int[MAXN];

	public static int[] dist = new int[MAXN];

	public static int[] diam = new int[MAXN];

	public static void build(int n) {
		cnt = 1;
		Arrays.fill(head, 1, n, 0);
		for (int i = 1; i <= n; i++) {
			father[i] = i;
		}
		Arrays.fill(dist, 1, n, 0);
		Arrays.fill(diam, 1, n, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static int find(int i) {
		if (i != father[i]) {
			father[i] = find(father[i]);
		}
		return father[i];
	}

	public static void dfs(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
			}
		}
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				diam[u] = Math.max(diam[u], Math.max(diam[v], dist[u] + dist[v] + 1));
				dist[u] = Math.max(dist[u], dist[v] + 1);
			}
		}
	}

	public static int half(int v) {
		return (v + 1) / 2;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		in.nextToken();
		int m = (int) in.nval;
		in.nextToken();
		int q = (int) in.nval;
		build(n);
		for (int i = 1, u, v; i <= m; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
			u = find(u);
			v = find(v);
			father[u] = v;
		}
		for (int i = 1; i <= n; i++) {
			if (father[i] == i) {
				dfs(i, 0);
			}
		}
		for (int i = 1, op, u, v; i <= q; i++) {
			in.nextToken();
			op = (int) in.nval;
			if (op == 1) {
				in.nextToken();
				u = (int) in.nval;
				u = find(u);
				out.println(diam[u]);
			} else {
				in.nextToken();
				u = (int) in.nval;
				in.nextToken();
				v = (int) in.nval;
				u = find(u);
				v = find(v);
				if (u != v) {
					father[u] = v;
					diam[v] = Math.max(half(diam[u]) + half(diam[v]) + 1, Math.max(diam[u], diam[v]));
				}
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}

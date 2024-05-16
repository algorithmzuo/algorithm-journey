package class123;

// 测试链接 : https://www.luogu.com.cn/problem/CF708C

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code06_Centroids {

	public static int MAXN = 400001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static int[] max1 = new int[MAXN];

	public static int[] max2 = new int[MAXN];

	public static int[] inner = new int[MAXN];

	public static int[] outer = new int[MAXN];

	public static boolean[] ans = new boolean[MAXN];

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(max1, 1, n + 1, 0);
		Arrays.fill(max2, 1, n + 1, 0);
		Arrays.fill(inner, 1, n + 1, 0);
		Arrays.fill(outer, 1, n + 1, 0);
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs1(int u, int f) {
		size[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs1(v, u);
				size[u] += size[v];
				if (size[max1[u]] < size[v]) {
					max2[u] = max1[u];
					max1[u] = v;
				} else if (size[max2[u]] < size[v]) {
					max2[u] = v;
				}
				inner[u] = Math.max(inner[u], inner[v]);
			}
		}
		if (size[u] <= n / 2) {
			inner[u] = size[u];
		}
	}

	public static void dfs2(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				if (n - size[v] <= n / 2) {
					outer[v] = n - size[v];
				} else if (v != max1[u]) {
					outer[v] = Math.max(outer[u], inner[max1[u]]);
				} else {
					outer[v] = Math.max(outer[u], inner[max2[u]]);
				}
				dfs2(v, u);
			}
		}
		if (n - size[u] > size[max1[u]]) {
			ans[u] = (n - size[u] - outer[u] <= n / 2);
		} else {
			ans[u] = (size[max1[u]] - inner[max1[u]] <= n / 2);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		n = (int) in.nval;
		build();
		for (int i = 1, u, v; i < n; i++) {
			in.nextToken();
			u = (int) in.nval;
			in.nextToken();
			v = (int) in.nval;
			addEdge(u, v);
			addEdge(v, u);
		}
		dfs1(1, 0);
		dfs2(1, 0);
		for (int i = 1; i <= n; i++) {
			out.print(ans[i] ? "1 " : "0 ");
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}

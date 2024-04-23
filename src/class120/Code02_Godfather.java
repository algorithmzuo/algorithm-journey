package class120;

// 测试链接 : http://poj.org/problem?id=3107

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_Godfather {

	public static int MAXN = 50001;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static int n;

	public static int[] ans = new int[MAXN];

	public static int maxSize, m;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		maxSize = Integer.MAX_VALUE;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs(int u, int f) {
		size[u] = 1;
		int max = 0;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				max = Math.max(max, size[v]);
			}
		}
		max = Math.max(max, n - size[u]);
		if (max < maxSize) {
			maxSize = max;
			m = 0;
			ans[++m] = u;
		} else if (max == maxSize) {
			ans[++m] = u;
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
		dfs(1, 0);
		Arrays.sort(ans, 1, m + 1);
		out.print(ans[1]);
		for (int i = 2; i <= m; i++) {
			out.print(" " + ans[i]);
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

}

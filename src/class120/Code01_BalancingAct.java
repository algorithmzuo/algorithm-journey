package class120;

// 测试链接 : http://poj.org/problem?id=1655

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code01_BalancingAct {

	public static int MAXN = 20001;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static int n;

	public static int bestSize, node;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		bestSize = Integer.MAX_VALUE;
		node = 0;
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
		if (max < bestSize || (max == bestSize && u < node)) {
			bestSize = max;
			node = u;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int testCase = (int) in.nval;
		for (int t = 1; t <= testCase; t++) {
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
			out.println(node + " " + bestSize);
		}
		out.flush();
		out.close();
		br.close();
	}

}

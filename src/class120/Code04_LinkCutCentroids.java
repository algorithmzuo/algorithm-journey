package class120;

// 测试链接 : https://www.luogu.com.cn/problem/CF1406C

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code04_LinkCutCentroids {

	public static int MAXN = 100001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int cnt;

	public static int[] size = new int[MAXN];

	public static int[] cutSize = new int[MAXN];

	public static int bestSize;

	public static int[] bests = new int[MAXN];

	public static int node1, node2;

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
			if (compute() == 1) {
				out.println(bests[1] + " " + to[head[bests[1]]]);
				out.println(bests[1] + " " + to[head[bests[1]]]);
			} else {
				out.println(node2 + " " + node1);
				out.println(bests[1] + " " + node1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		bestSize = n + 1;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static int compute() {
		dfs(1, 0);
		int m = 0;
		for (int i = 1; i <= n; i++) {
			if (cutSize[i] == bestSize) {
				bests[++m] = i;
			}
		}
		if (m != 1) {
			find(bests[2], bests[1]);
		}
		return m;
	}

	public static void dfs(int u, int f) {
		size[u] = cutSize[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				cutSize[u] = Math.max(cutSize[u], size[v]);
			}
		}
		cutSize[u] = Math.max(cutSize[u], n - size[u]);
		if (cutSize[u] < bestSize) {
			bestSize = cutSize[u];
		}
	}

	public static void find(int u, int f) {
		for (int e = head[u]; e != 0; e = next[e]) {
			if (to[e] != f) {
				find(to[e], u);
				return;
			}
		}
		node1 = u;
		node2 = f;
	}

}

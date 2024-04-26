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

	public static int[] maxSize = new int[MAXN];

	public static int[] nodes = new int[MAXN];

	public static int min;

	public static int tmp1, tmp2;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		min = n + 1;
	}

	public static void addEdge(int u, int v) {
		next[cnt] = head[u];
		to[cnt] = v;
		head[u] = cnt++;
	}

	public static void dfs(int u, int f) {
		size[u] = maxSize[u] = 1;
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				dfs(v, u);
				size[u] += size[v];
				maxSize[u] = Math.max(maxSize[u], size[v]);
			}
		}
		maxSize[u] = Math.max(maxSize[u], n - size[u]);
		if (maxSize[u] < min) {
			min = maxSize[u];
		}
	}

	public static int find(int u, int f) {
		for (int e = head[u], v; e != 0; e = next[e]) {
			v = to[e];
			if (v != f) {
				tmp2 = u;
				return find(v, u);
			}
		}
		return u;
	}

	public static int compute() {
		dfs(1, 0);
		int m = 0;
		for (int i = 1; i <= n; i++) {
			if (maxSize[i] == min) {
				nodes[++m] = i;
			}
		}
		if (m != 1) {
			tmp1 = find(nodes[2], nodes[1]);
		}
		return m;
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
			if (compute() == 1) {
				out.println(nodes[1] + " " + to[head[nodes[1]]]);
				out.println(nodes[1] + " " + to[head[nodes[1]]]);
			} else {
				out.println(tmp2 + " " + tmp1);
				out.println(nodes[1] + " " + tmp1);
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}

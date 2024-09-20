package class121;

// 所有直径的公共部分(迭代版)
// 给定一棵树，边权都为正
// 打印直径长度、所有直径的公共部分有几条边
// 测试链接 : https://www.luogu.com.cn/problem/P3304
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code02_DiameterAndCommonEdges2 {

	public static int MAXN = 200001;

	public static int n;

	public static int[] head = new int[MAXN];

	public static int[] next = new int[MAXN << 1];

	public static int[] to = new int[MAXN << 1];

	public static int[] weight = new int[MAXN << 1];

	public static int cnt;

	public static int start;

	public static int end;

	public static long[] dist = new long[MAXN];

	public static int[] last = new int[MAXN];

	public static long diameter;

	public static boolean[] diameterPath = new boolean[MAXN];

	public static int commonEdges;

	public static void build() {
		cnt = 1;
		Arrays.fill(head, 1, n + 1, 0);
		Arrays.fill(diameterPath, 1, n + 1, false);
	}

	public static void addEdge(int u, int v, int w) {
		next[cnt] = head[u];
		to[cnt] = v;
		weight[cnt] = w;
		head[u] = cnt++;
	}

	public static void road() {
		dfs(1);
		start = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[start]) {
				start = i;
			}
		}
		dfs(start);
		end = 1;
		for (int i = 2; i <= n; i++) {
			if (dist[i] > dist[end]) {
				end = i;
			}
		}
		diameter = dist[end];
	}

	// dfs方法改迭代版
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
	public static int[][] ufeStack = new int[MAXN][3];

	public static long[] distStack = new long[MAXN];

	public static int stackSize;

	public static int u, f, e;

	public static long c;

	public static void push(int u, int f, int e, long c) {
		ufeStack[stackSize][0] = u;
		ufeStack[stackSize][1] = f;
		ufeStack[stackSize][2] = e;
		distStack[stackSize] = c;
		stackSize++;
	}

	public static void pop() {
		--stackSize;
		u = ufeStack[stackSize][0];
		f = ufeStack[stackSize][1];
		e = ufeStack[stackSize][2];
		c = distStack[stackSize];
	}

	public static void dfs(int root) {
		stackSize = 0;
		push(root, 0, -1, 0);
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				last[u] = f;
				dist[u] = c;
				e = head[u];
			} else {
				e = next[e];
			}
			if (e != 0) {
				push(u, f, e, c);
				if (to[e] != f) {
					push(to[e], u, -1, c + weight[e]);
				}
			}
		}
	}

	// maxDistanceExceptDiameter方法改迭代版
	// 不会改，看讲解118，讲了怎么从递归版改成迭代版
	public static long maxDistanceExceptDiameter(int root) {
		stackSize = 0;
		push(root, 0, -1, 0);
		long ans = 0;
		while (stackSize > 0) {
			pop();
			if (e == -1) {
				e = head[u];
			} else {
				e = next[e];
			}
			int v;
			if (e != 0) {
				push(u, f, e, c);
				v = to[e];
				if (!diameterPath[v] && v != f) {
					push(v, u, -1, c + weight[e]);
				}
			} else {
				ans = Math.max(ans, c);
			}
		}
		return ans;
	}

	public static void compute() {
		road();
		for (int i = end; i != 0; i = last[i]) {
			diameterPath[i] = true;
		}
		int l = start;
		int r = end;
		long maxDist;
		for (int i = last[end]; i != start; i = last[i]) {
			maxDist = maxDistanceExceptDiameter(i);
			if (maxDist == diameter - dist[i]) {
				r = i;
			}
			if (maxDist == dist[i] && l == start) {
				l = i;
			}
		}
		if (l == r) {
			commonEdges = 0;
		} else {
			commonEdges = 1;
			for (int i = last[r]; i != l; i = last[i]) {
				commonEdges++;
			}
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

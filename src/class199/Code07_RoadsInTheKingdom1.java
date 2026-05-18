package class199;

// 王国里的道路，java版
// 测试链接 : https://www.luogu.com.cn/problem/CF835F
// 测试链接 : https://codeforces.com/problemset/problem/835/F
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code07_RoadsInTheKingdom1 {

	public static int MAXN = 200001;
	public static int n;

	public static int[] head = new int[MAXN];
	public static int[] nxt = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int[] weight = new int[MAXN << 1];
	public static int cntg;

	public static int[] dfn = new int[MAXN];
	public static int cntd;

	public static int[] fromNode = new int[MAXN];
	public static int[] fromWeight = new int[MAXN];
	public static boolean[] cycle = new boolean[MAXN];
	public static int[] arr = new int[MAXN];
	public static int[] val = new int[MAXN];
	public static int cnta;

	public static long[] dist = new long[MAXN];
	public static long diameter;

	public static long[] height = new long[MAXN];
	public static long[] preMax = new long[MAXN];
	public static long[] preDiameter = new long[MAXN];
	public static long[] sufMax = new long[MAXN + 1];
	public static long[] sufDiameter = new long[MAXN + 1];

	public static void addEdge(int u, int v, int w) {
		nxt[++cntg] = head[u];
		to[cntg] = v;
		weight[cntg] = w;
		head[u] = cntg;
	}

	public static void dfs(int u, int preEdge) {
		dfn[u] = ++cntd;
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			if (e != (preEdge ^ 1)) {
				if (dfn[v] == 0) {
					fromNode[v] = u;
					fromWeight[v] = weight[e];
					dfs(v, e);
				} else if (dfn[u] < dfn[v]) {
					cycle[u] = true;
					arr[++cnta] = u;
					val[cnta] = weight[e];
					for (int i = v; i != u; i = fromNode[i]) {
						cycle[i] = true;
						arr[++cnta] = i;
						val[cnta] = fromWeight[i];
					}
				}
			}
		}
	}

	public static void dpOnTree(int u, int fa) {
		for (int e = head[u]; e > 0; e = nxt[e]) {
			int v = to[e];
			int w = weight[e];
			if (v != fa && !cycle[v]) {
				dpOnTree(v, u);
				diameter = Math.max(diameter, dist[u] + dist[v] + w);
				dist[u] = Math.max(dist[u], dist[v] + w);
			}
		}
	}

	public static long dpOnCycle() {
		long sum = 0;
		long best = 0;
		for (int i = 1; i <= cnta; i++) {
			preMax[i] = Math.max(preMax[i - 1], height[i] + sum);
			preDiameter[i] = Math.max(preDiameter[i - 1], sum + height[i] + best);
			best = Math.max(best, height[i] - sum);
			sum += val[i];
		}
		sum = val[cnta];
		best = -val[cnta];
		for (int i = cnta; i >= 1; i--) {
			sufMax[i] = Math.max(sufMax[i + 1], height[i] + sum);
			sufDiameter[i] = Math.max(sufDiameter[i + 1], sum + height[i] + best);
			best = Math.max(best, height[i] - sum);
			sum += val[i - 1];
		}
		long ans = preDiameter[cnta];
		for (int i = 1; i < cnta; i++) {
			ans = Math.min(ans, Math.max(preMax[i] + sufMax[i + 1], Math.max(preDiameter[i], sufDiameter[i + 1])));
		}
		return ans;
	}

	public static long compute() {
		dfs(1, 0);
		long ans1 = 0;
		for (int i = 1; i <= cnta; i++) {
			diameter = 0;
			dpOnTree(arr[i], 0);
			ans1 = Math.max(ans1, diameter);
			height[i] = dist[arr[i]];
		}
		long ans2 = dpOnCycle();
		return Math.max(ans1, ans2);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		cntg = 1;
		for (int i = 1, u, v, w; i <= n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			w = in.nextInt();
			addEdge(u, v, w);
			addEdge(v, u, w);
		}
		long ans = compute();
		out.println(ans);
		out.flush();
		out.close();
	}

	// 读写工具类
	static class FastReader {

		private final byte[] buffer = new byte[1 << 16];
		private int ptr = 0, len = 0;
		private final InputStream in;

		FastReader(InputStream in) {
			this.in = in;
		}

		private int readByte() throws IOException {
			if (ptr >= len) {
				len = in.read(buffer);
				ptr = 0;
				if (len <= 0)
					return -1;
			}
			return buffer[ptr++];
		}

		int nextInt() throws IOException {
			int c;
			do {
				c = readByte();
			} while (c <= ' ' && c != -1);
			boolean neg = false;
			if (c == '-') {
				neg = true;
				c = readByte();
			}
			int val = 0;
			while (c > ' ' && c != -1) {
				val = val * 10 + (c - '0');
				c = readByte();
			}
			return neg ? -val : val;
		}
	}

}

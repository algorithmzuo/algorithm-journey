package class188;

// 丁香之路，java版
// 测试链接 : https://www.luogu.com.cn/problem/P6628
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Code11_DingXiangRoad1 {

	public static int MAXN = 3001;
	public static int MAXM = 3000001;
	public static int n, m, s;
	public static int[] a = new int[MAXM];
	public static int[] b = new int[MAXM];

	public static boolean[] fix = new boolean[MAXN];
	public static TreeSet<Integer> nodeSet = new TreeSet<>();
	public static int[] nodeArr = new int[MAXN];
	public static int cnt;

	public static int[] fa1 = new int[MAXN];
	public static int[] deg1 = new int[MAXN];
	public static int[] fa2 = new int[MAXN];
	public static int[] deg2 = new int[MAXN];

	// u、v、w
	public static int[][] edgeArr = new int[MAXN][3];

	public static long sum;

	public static class EdgeCmp implements Comparator<int[]> {
		@Override
		public int compare(int[] e1, int[] e2) {
			if (e1[2] != e2[2]) {
				return e1[2] - e2[2];
			}
			if (e1[0] != e2[0]) {
				return e1[0] - e2[0];
			}
			return e1[1] - e2[1];
		}
	}

	public static int dist(int x, int y) {
		return Math.abs(x - y);
	}

	public static int find(int[] fa, int x) {
		if (x != fa[x]) {
			fa[x] = find(fa, fa[x]);
		}
		return fa[x];
	}

	public static void union(int[] fa, int x, int y) {
		int fx = find(fa, x);
		int fy = find(fa, y);
		if (fx != fy) {
			fa[fx] = fy;
		}
	}

	public static long kruskal() {
		long cost = 0;
		int edgeCnt = cnt - 1;
		Arrays.sort(edgeArr, 1, edgeCnt + 1, new EdgeCmp());
		for (int i = 1; i <= edgeCnt; i++) {
			int u = edgeArr[i][0];
			int v = edgeArr[i][1];
			int w = edgeArr[i][2];
			if (find(fa2, u) != find(fa2, v)) {
				cost += w * 2;
				union(fa2, u, v);
			}
		}
		return cost;
	}

	public static long compute(int start, int end) {
		cnt = 0;
		for (int x : nodeSet) {
			nodeArr[++cnt] = x;
		}
		for (int i = 1; i <= n; i++) {
			fa2[i] = find(fa1, i);
			deg2[i] = deg1[i];
		}
		long ans = sum;
		deg2[start]++;
		deg2[end]++;
		union(fa2, start, end);
		for (int i = 1, pre = 0, cur; i <= cnt; i++) {
			cur = nodeArr[i];
			if ((deg2[cur] & 1) == 1) {
				if (pre == 0) {
					pre = i;
				} else {
					ans += dist(nodeArr[pre], cur);
					for (int k = pre; k <= i; k++) {
						union(fa2, nodeArr[k], cur);
					}
					pre = 0;
				}
			}
		}
		for (int i = 1; i <= cnt - 1; i++) {
			edgeArr[i][0] = nodeArr[i];
			edgeArr[i][1] = nodeArr[i + 1];
			edgeArr[i][2] = nodeArr[i + 1] - nodeArr[i];
		}
		ans += kruskal();
		return ans;
	}

	public static void prepare() {
		fix[s] = true;
		nodeSet.add(s);
		sum = 0;
		for (int i = 1; i <= n; i++) {
			fa1[i] = i;
		}
		for (int i = 1, u, v; i <= m; i++) {
			u = a[i];
			v = b[i];
			deg1[u]++;
			deg1[v]++;
			fix[u] = true;
			fix[v] = true;
			nodeSet.add(u);
			nodeSet.add(v);
			union(fa1, u, v);
			sum += dist(u, v);
		}
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		n = in.nextInt();
		m = in.nextInt();
		s = in.nextInt();
		for (int i = 1; i <= m; i++) {
			a[i] = in.nextInt();
			b[i] = in.nextInt();
		}
		prepare();
		for (int i = 1; i <= n; i++) {
			if (!fix[i]) {
				nodeSet.add(i);
			}
			out.print(compute(s, i) + " ");
			if (!fix[i]) {
				nodeSet.remove(i);
			}
		}
		out.println();
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

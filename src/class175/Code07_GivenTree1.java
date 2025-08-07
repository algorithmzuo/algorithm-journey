package class175;

// 给你一棵树，java版
// 一共有n个节点，给定n-1条边，所有节点连成一棵树
// 树的路径是指，从端点x到端点y的简单路径，k路径是指，路径上节点数正好为k
// 那么整棵树可以分解出若干条路径，路径之间不能重叠，所有路径也不要求覆盖所有点
// 希望分解方案中，k路径的数量尽可能多，返回这个尽可能多的数量作为答案
// 打印k = 1, 2, 3..n时，所有的答案
// 测试链接 : https://www.luogu.com.cn/problem/CF1039D
// 测试链接 : https://codeforces.com/problemset/problem/1039/D
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class Code07_GivenTree1 {

	public static int MAXN = 200001;
	public static int n, blen;
	public static int[] head = new int[MAXN];
	public static int[] next = new int[MAXN << 1];
	public static int[] to = new int[MAXN << 1];
	public static int cntg = 0;

	// fa[i]表示，i节点的父节点编号
	// seg[d]表示，dfn序号为d的节点，在图上的编号是什么
	public static int[] fa = new int[MAXN];
	public static int[] seg = new int[MAXN];
	public static int cntd = 0;

	// len[i]表示，当前i号节点只能往下走，没分配成路径的最长链的长度
	// max1[i]表示，最大值 { len[a], len[b], len[c] ... }，其中a、b、c..是i的子节点
	// max2[i]表示，次大值 { len[a], len[b], len[c] ... }，其中a、b、c..是i的子节点
	public static int[] len = new int[MAXN];
	public static int[] max1 = new int[MAXN];
	public static int[] max2 = new int[MAXN];

	public static int[] ans = new int[MAXN];

	public static void addEdge(int u, int v) {
		next[++cntg] = head[u];
		to[cntg] = v;
		head[u] = cntg;
	}

	public static void dfs(int u, int f) {
		fa[u] = f;
		seg[++cntd] = u;
		for (int e = head[u]; e > 0; e = next[e]) {
			if (to[e] != f) {
				dfs(to[e], u);
			}
		}
	}

	public static int query(int limit) {
		int cnt = 0;
		for (int dfn = n, cur, father; dfn >= 1; dfn--) {
			cur = seg[dfn];
			father = fa[cur];
			if (max1[cur] + max2[cur] + 1 >= limit) {
				cnt++;
				len[cur] = 0;
			} else {
				len[cur] = max1[cur] + 1;
			}
			if (len[cur] > max1[father]) {
				max2[father] = max1[father];
				max1[father] = len[cur];
			} else if (len[cur] > max2[father]) {
				max2[father] = len[cur];
			}
		}
		for (int i = 1; i <= n; i++) {
			len[i] = max1[i] = max2[i] = 0;
		}
		return cnt;
	}

	public static int jump(int l, int r, int curAns) {
		int find = l;
		while (l <= r) {
			int mid = (l + r) >> 1;
			int check = query(mid);
			if (check < curAns) {
				r = mid - 1;
			} else if (check > curAns) {
				l = mid + 1;
			} else {
				find = mid;
				l = mid + 1;
			}
		}
		return find + 1;
	}

	public static void compute() {
		for (int i = 1; i <= blen; i++) {
			ans[i] = query(i);
		}
		for (int i = blen + 1; i <= n; i = jump(i, n, ans[i])) {
			ans[i] = query(i);
		}
	}

	public static void prepare() {
		dfs(1, 0);
		int log2n = 0;
		while ((1 << log2n) <= (n >> 1)) {
			log2n++;
		}
		blen = Math.max(1, (int) Math.sqrt(n * log2n));
		Arrays.fill(ans, 1, n + 1, -1);
	}

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter out = new PrintWriter(System.out);
		n = in.nextInt();
		for (int i = 1, u, v; i < n; i++) {
			u = in.nextInt();
			v = in.nextInt();
			addEdge(u, v);
			addEdge(v, u);
		}
		prepare();
		compute();
		for (int i = 1; i <= n; i++) {
			if (ans[i] == -1) {
				ans[i] = ans[i - 1];
			}
			out.println(ans[i]);
		}
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
